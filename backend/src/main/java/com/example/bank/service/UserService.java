package com.example.bank.service;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Random;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AccountService accountService;
    
    public User createUser(User user) {
        // Validate required fields
        if (user.getFirstName() == null || user.getLastName() == null) {
            throw new RuntimeException("Please provide both first name and last name.");
        }
        
        // Check if email or citizenship number already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        
        if (userRepository.existsByCitizenshipNumber(user.getCitizenshipNumber())) {
            throw new RuntimeException("Citizenship number already registered.");
        }
        
        // Generate account number
        String accountNumber = generateAccountNumber();
        user.setAccountNumber(accountNumber);
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Create account with initial balance
        accountService.createAccount(savedUser);
        
        return savedUser;
    }
    
    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = String.format("%010d", random.nextInt(1000000000));
        } while (userRepository.existsByAccountNumber(accountNumber));
        
        return accountNumber;
    }
    
    public User getUserByAccountNumber(String accountNumber) {
        return userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    // This method is now handled by DeletionService
    // We keep a simple delete method for User only (without associated data)
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}