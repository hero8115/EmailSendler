package com.example.emailsendler.repository;

import com.example.emailsendler.entity.Addition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdditionRepository extends JpaRepository<Addition, UUID> {
    Optional<Addition> findByEmail(String sendingEmail);
}
