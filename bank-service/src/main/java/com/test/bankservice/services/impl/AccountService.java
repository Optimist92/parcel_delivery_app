package com.test.bankservice.services.impl;

import com.test.bankservice.domain.Account;
import com.test.bankservice.repository.IAccountRepository;
import com.test.bankservice.services.IAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
//@Scope("prototype")
@RequiredArgsConstructor
@Validated
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    private final Map<UUID, Object> lockerMap = new HashMap();
    private final Map<UUID, ReentrantLock> reentrantLockerMap = new HashMap();

    //private Object locker;

    @Transactional
    public Account reserveAmount(Account account, BigDecimal delta) {

        //locker = getLocker(account);
        try {
            synchronized (getAccountLocker(account)) {

                if (account.getAvailableAmount().compareTo(delta) < 0)
                    throw new RuntimeException("not enough funds on " + account.getNumber());

                var currentAvailableAmount = account.getAvailableAmount();
                var currentReservedAmount = account.getReservedAmount();
                var newAvailableAmount = currentAvailableAmount.subtract(delta);
                var newReservedAmount = currentReservedAmount.add(delta);
                account.setAvailableAmount(newAvailableAmount);
                account.setReservedAmount(newReservedAmount);

                //account.reserveAmount(new BigDecimal(1.00));

                account = accountRepository.save(account);

                return account;
            }
        }
        /*catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace());
        }*/
        finally {
            lockerMap.remove(account.getId());
        }
    }

    public Object getAccountLocker(Account account) {

        var accountId = account.getId();
        if (!lockerMap.containsKey(accountId)) {
            lockerMap.put(accountId, new Object());
        }
        return lockerMap.get(accountId);
    }

    @Transactional
    public Account reserveAmount2(Account account, BigDecimal delta) {

        var reLock = getAccountReentrantLocker(account);
        try {
            reLock.lockInterruptibly();

            if (account.getAvailableAmount().compareTo(delta) < 0)
                throw new RuntimeException("not enough funds on " + account.getNumber());

            var currentAvailableAmount = account.getAvailableAmount();
            var currentReservedAmount = account.getReservedAmount();
            var newAvailableAmount = currentAvailableAmount.subtract(delta);
            var newReservedAmount = currentReservedAmount.add(delta);
            account.setAvailableAmount(newAvailableAmount);
            account.setReservedAmount(newReservedAmount);

            //account.reserveAmount(new BigDecimal(1.00));

            account = accountRepository.save(account);

            return account;
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace());
            //return account;
            throw new RuntimeException(e);
        }
        finally {
            reLock.unlock();
            reentrantLockerMap.remove(account.getId());
        }
    }
    public ReentrantLock getAccountReentrantLocker(Account account) {

        var accountId = account.getId();
        if (!reentrantLockerMap.containsKey(accountId)) {
            reentrantLockerMap.put(accountId, new ReentrantLock());
        }
        return reentrantLockerMap.get(accountId);
    }


    /*
    @Transactional
    public synchronized Account updateAvailableAmount(Account account, BigDecimal delta) {
        var currentAvailableAmount = account.getAvailableAmount();
        var newAvailableAmount = currentAvailableAmount.add(delta);
        account.setAvailableAmount(newAvailableAmount);
        return accountRepository.save(account);
    }

    @Transactional
    public synchronized Account updateReservedAmount(Account account, BigDecimal delta) {
        var currentReservedAmount = account.getAvailableAmount();
        var newReservedAmount = currentReservedAmount.add(delta);
        account.setReservedAmount(newReservedAmount);
        return accountRepository.save(account);
    }*/
}
