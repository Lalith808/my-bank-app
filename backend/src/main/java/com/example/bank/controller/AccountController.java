package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.model.User;
import com.example.bank.service.AccountService;
import com.example.bank.service.AccountDetailsService;
import com.example.bank.service.DeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {

    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountDetailsService accountDetailsService;
    
    @Autowired
    private DeletionService deletionService;
    
    @GetMapping("/balance/{userId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long userId) {
        try {
            BigDecimal balance = accountService.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/details/{accountNumber}")
    public ResponseEntity<User> getAccountDetails(@PathVariable String accountNumber) {
        try {
            User user = accountDetailsService.getAccountDetails(accountNumber);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long userId) {
        try {
            Account account = accountService.getAccountByUserId(userId);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long userId) {
        try {
            deletionService.deleteUserAccount(userId);
            return ResponseEntity.ok().body("Account successfully deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}