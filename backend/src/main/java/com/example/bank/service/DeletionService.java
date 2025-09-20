package com.example.bank.service;

import java.util.List;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.model.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletionService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AccountService accountService;
    
    /**
     * Delete a user and all associated data (account, transactions)
     * This is equivalent to your original accCloseFun method
     */
    @Transactional
    public void deleteUserAccount(Long userId) {
        User user = userService.getUserById(userId);
        Account account = accountService.getAccountByUserId(userId);
        
        // Delete all transactions associated with the account
        deleteTransactionsForAccount(account);
        
        // Delete the account
        accountRepository.delete(account);
        
        // Delete the user
        userRepository.delete(user);
    }
    
    /**
     * Delete all transactions for a specific account
     */
    @Transactional
    public void deleteTransactionsForAccount(Account account) {
        // ✅ FIXED: Use correct method name
        List<Transaction> transactions = transactionRepository.findByAccountOrderByTransactionDateDesc(account);
        transactionRepository.deleteAll(transactions);
    }
    
    /**
     * Delete a specific transaction by ID
     */
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));
        transactionRepository.delete(transaction);
    }
}