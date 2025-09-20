package com.example.bank.repository;

import com.example.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccountNumber(String accountNumber);
    Optional<User> findByEmail(String email);
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByEmail(String email);
    boolean existsByCitizenshipNumber(String citizenshipNumber);
    Optional<User> findById(Long id); // Add this if missing
}