package com.example.reditClone.repository;

import com.example.reditClone.model.VerifcationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerifcationToken, Long> {
    Optional<VerifcationToken> findByToken(String token);
}
