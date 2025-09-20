package com.example.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Foreign Keys ---
    @Column(name = "from_account_id")
    private Long fromAccountId;

    @Column(name = "to_account_id")
    private Long toAccountId;

    // --- Core Transaction Fields (matching your data.sql) ---
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType; // e.g., "DEPOSIT", "TRANSFER", "WITHDRAWAL"

    @Column(name = "remarks")
    private String remarks; // Optional description

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    // --- Relationship ---
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // --- Constructors ---
    public Transaction() {
        this.transactionDate = LocalDateTime.now();
    }

    // Constructor matching your data.sql usage
    public Transaction(Long fromAccountId, Long toAccountId, BigDecimal amount,
                       String transactionType, String remarks, Account account) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.remarks = remarks;
        this.account = account;
        this.transactionDate = LocalDateTime.now();
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(Long fromAccountId) { this.fromAccountId = fromAccountId; }

    public Long getToAccountId() { return toAccountId; }
    public void setToAccountId(Long toAccountId) { this.toAccountId = toAccountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }
}