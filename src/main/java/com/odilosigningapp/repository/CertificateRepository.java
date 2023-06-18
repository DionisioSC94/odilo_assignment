package com.odilosigningapp.repository;

import com.odilosigningapp.Models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findByUserId(Long userId);
}
