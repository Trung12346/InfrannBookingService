package com.example.demo.repository;

import com.example.demo.model.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Integer> {
}
