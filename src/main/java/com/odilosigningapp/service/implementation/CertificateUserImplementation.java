package com.odilosigningapp.service.implementation;

import com.odilosigningapp.Models.Certificate;
import com.odilosigningapp.repository.CertificateRepository;
import com.odilosigningapp.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateUserImplementation implements CertificateService {
    @Autowired
    CertificateRepository certificateRepository;

    @Override
    public Certificate findCertificateByUserId(Long userId) {
        return certificateRepository.findByUserId(userId);
    }
}
