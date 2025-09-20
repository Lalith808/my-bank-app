package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.User;
import com.example.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Account createAccount(User user) {
        Account account = new Account(user);
        return accountRepository.save(account);
    }
    
    public BigDecimal getBalance(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }
    
    public Account getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
    
    // Add this method if it's missing
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }
}