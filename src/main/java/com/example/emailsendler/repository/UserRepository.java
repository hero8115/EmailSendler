package com.example.emailsendler.repository;

import com.example.emailsendler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);


    Optional<User> findByEmailAndEmailCode(String email,String emailCode);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean deleteByEmail(String email);

    Optional<User> findByUsername(String username);
}
