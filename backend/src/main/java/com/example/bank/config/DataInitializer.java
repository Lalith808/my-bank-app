// src/main/java/com/example/bank/config/DataInitializer.java

package com.example.bank.config;

import com.example.bank.model.Account;
import com.example.bank.model.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUserData(UserRepository userRepository, AccountRepository accountRepository) {
        return args -> {
            // Check if user already exists
            if (userRepository.findByAccountNumber("ACC1234567").isEmpty()) {
                User user = new User();
                user.setAccountNumber("ACC1234567");
                user.setPassword("password123");
                user.setFirstName("Alice");
                user.setLastName("Smith");
                user.setDateOfBirth("1990-05-15");
                user.setGender("Female");
                user.setAddress("123 Main St, City");
                user.setPhoneNumber("555-1234");
                user.setEmail("alice@example.com");
                user.setCitizenshipNumber("CIT123456789");

                user = userRepository.save(user);
                System.out.println("✅ Test user inserted: " + user.getFirstName());

                // Create account for user
                Account account = new Account();
                account.setUser(user);
                account.setBalance(new BigDecimal("5000.00"));
                accountRepository.save(account);
                System.out.println("✅ Test account inserted for user ID: " + user.getId());
            }
        };
    }
}