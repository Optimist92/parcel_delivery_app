package com.test.bankservice;

import com.test.bankservice.domain.Account;
import com.test.bankservice.domain.Card;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServiceApplication.class, args);
        /*var card = new Card();
        card.getAccount();
        var account = new Account();
        account.getId();*/
    }

}
