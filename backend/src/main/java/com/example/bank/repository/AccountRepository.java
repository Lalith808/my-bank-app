package com.example.bank.repository;

import com.example.bank.model.Account;
import com.example.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
    Optional<Account> findByUserId(Long userId);
    Optional<Account> findById(Long id); // Add this if missing
}
