package com.example.bank.service;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User login(String accountNumber, String password) {
        Optional<User> user = userRepository.findByAccountNumber(accountNumber);
        
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        } else if (user.isPresent()) {
            throw new RuntimeException("Incorrect password!");
        } else {
            throw new RuntimeException("Account doesn't exist!");
        }
    }
}