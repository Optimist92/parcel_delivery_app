package com.test.bankservice.services.impl;

import com.test.bankservice.domain.Customer;
import com.test.bankservice.domain.User;
import com.test.bankservice.repository.IUserRepository;
import com.test.bankservice.services.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService extends UserService implements ICustomerService {

        public CustomerService(IUserRepository userRepository) {
            super(userRepository);
        }

        public Optional<User> findByUsername(String username) {
            var customer = (Customer)super.findByUsername(username).get();
            System.out.println("customerId = " + customer.getCustomerId());
            return Optional.ofNullable(customer);
        }
}
