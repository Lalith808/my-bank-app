package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.model.User;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AccountService accountService;
    
    public List<Transaction> getTransactionHistory(Long userId) {
        Account account = accountService.getAccountByUserId(userId);
        return transactionRepository.findByAccountOrderByTransactionDateDesc(account); // ✅ Updated method name (see note below)
    }
    
    @Transactional
    public void transferFunds(Long fromUserId, String toAccountNumber, BigDecimal amount, String remarks) {
        // Get sender's account
        Account fromAccount = accountService.getAccountByUserId(fromUserId);
        User fromUser = fromAccount.getUser();
        
        // Check if receiver exists
        User toUser = userRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));
        
        Account toAccount = accountService.getAccountByUserId(toUser.getId());
        
        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Perform transfer
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        
        // Record debit transaction (sender)
        Transaction debitTransaction = new Transaction();
        debitTransaction.setRemarks("Transfer to " + toAccountNumber); // ✅ Was setDescription
        debitTransaction.setTransactionType("DEBIT");                  // ✅ Was setType
        debitTransaction.setAmount(amount);
        debitTransaction.setRemarks(remarks);                          // Optional: combine or keep separate
        debitTransaction.setAccount(fromAccount);
        // ✅ transactionDate auto-set in constructor
        transactionRepository.save(debitTransaction);
        
        // Record credit transaction (receiver)
        Transaction creditTransaction = new Transaction();
        creditTransaction.setRemarks("Transfer from " + fromUser.getAccountNumber()); // ✅ Was setDescription
        creditTransaction.setTransactionType("CREDIT");               // ✅ Was setType
        creditTransaction.setAmount(amount);
        creditTransaction.setRemarks(remarks);
        creditTransaction.setAccount(toAccount);
        transactionRepository.save(creditTransaction);
    }
    
    @Transactional
    public void depositFunds(String accountNumber, BigDecimal amount, String remarks) {
        User user = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        Account account = accountService.getAccountByUserId(user.getId());
        
        // Update balance
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        
        // Record transaction
        Transaction depositTransaction = new Transaction();
        depositTransaction.setRemarks("Cash Deposit");                // ✅ Was setDescription
        depositTransaction.setTransactionType("CREDIT");              // ✅ Was setType
        depositTransaction.setAmount(amount);
        depositTransaction.setRemarks(remarks);
        depositTransaction.setAccount(account);
        transactionRepository.save(depositTransaction);
    }
    
    @Transactional
    public void withdrawFunds(Long userId, BigDecimal amount, String remarks) {
        Account account = accountService.getAccountByUserId(userId);
        
        // Check sufficient balance
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Update balance
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        
        // Record transaction
        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setRemarks("Cash Withdrawal");          // ✅ Was setDescription
        withdrawalTransaction.setTransactionType("DEBIT");            // ✅ Was setType
        withdrawalTransaction.setAmount(amount);
        withdrawalTransaction.setRemarks(remarks);
        withdrawalTransaction.setAccount(account);
        transactionRepository.save(withdrawalTransaction);
    }
}