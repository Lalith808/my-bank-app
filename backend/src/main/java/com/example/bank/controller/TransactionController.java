package com.example.bank.controller;

import com.example.bank.model.Transaction;
import com.example.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long userId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionHistory(userId);
            return ResponseEntity.ok(transactions);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    
    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody Map<String, Object> transferRequest) {
        try {
            Long fromUserId = Long.valueOf(transferRequest.get("fromUserId").toString());
            String toAccountNumber = transferRequest.get("toAccountNumber").toString();
            BigDecimal amount = new BigDecimal(transferRequest.get("amount").toString());
            String remarks = transferRequest.get("remarks") != null ? transferRequest.get("remarks").toString() : "";
            
            transactionService.transferFunds(fromUserId, toAccountNumber, amount, remarks);
            
            return ResponseEntity.ok().body("Transfer successful");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid number format");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/deposit")
public ResponseEntity<?> depositFunds(@RequestBody Map<String, Object> depositRequest) {
    try {
        String accountNumber = depositRequest.get("accountNumber").toString();
        BigDecimal amount = new BigDecimal(depositRequest.get("amount").toString());
        String remarks = depositRequest.get("remarks") != null ? depositRequest.get("remarks").toString() : "Deposit";

        transactionService.depositFunds(accountNumber, amount, remarks);

        // ✅ RETURN JSON OBJECT
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Deposit successful");
        response.put("amount", amount);
        response.put("accountNumber", accountNumber);

        return ResponseEntity.ok(response); // ← NOW IT'S JSON!
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
    
    @PostMapping("/withdraw")
public ResponseEntity<?> withdrawFunds(@RequestBody Map<String, Object> withdrawRequest) {
    try {
        Long userId = Long.valueOf(withdrawRequest.get("userId").toString());
        BigDecimal amount = new BigDecimal(withdrawRequest.get("amount").toString());
        String remarks = withdrawRequest.get("remarks") != null ? withdrawRequest.get("remarks").toString() : "Withdrawal";

        transactionService.withdrawFunds(userId, amount, remarks);

        // ✅ RETURN JSON OBJECT
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Withdrawal successful");
        response.put("amount", amount);
        response.put("userId", userId);

        return ResponseEntity.ok(response); // ← NOW IT'S JSON!
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
    @GetMapping("/test")
    public String test() {
        return "Spring Boot is working! Time: " + java.time.LocalDateTime.now();
    }
}