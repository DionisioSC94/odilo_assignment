package com.odilosigningapp.service;

import com.odilosigningapp.Models.Certificate;

public interface CertificateService {
    /**
     * Retrieves the Certificate object based on the user ID.
     *
     * @param userId the ID of the user (Long)
     * @return the certificate associated with the user ID, or null if not found (Certificate)
     */
    Certificate findCertificateByUserId(Long userId) ;
}
