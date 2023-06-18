package com.odilosigningapp.controller;

import com.odilosigningapp.Models.Certificate;
import com.odilosigningapp.Models.User;
import com.odilosigningapp.repository.CertificateRepository;
import com.odilosigningapp.service.CertificateService;
import com.odilosigningapp.service.S3Service;
import com.odilosigningapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class CertificateUploadController {
    private static final String FAILED_UPLOAD = "Upload failed. Contact administrator.";
    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    S3Service s3Service;

    @Autowired
    CertificateService certificateService;

    @Autowired
    UserService userService;

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("document") MultipartFile document,
                                 Model model,
                                 @RequestParam("password") String password,
                                 Authentication authentication) {

        try {
            // Save document file
            String fileName = document.getOriginalFilename();

            // Get authenticated user
            User user = userService.findAuthenticatedUser(authentication);

            Certificate previousCertificate = certificateService.findCertificateByUserId(user.getId());

            // delete old certificate in present
            if (previousCertificate != null) {
                try {
                    certificateRepository.delete(previousCertificate);
                    //  s3Service.deleteFile(previousCertificate.getPath());
                } catch (Exception e) {
                  //  log.error("Deletion of the old certified failed.");
                }
            }

            Certificate certificate = new Certificate();
            certificate.setUser(user);
            certificate.setPassword(password);
            certificate.setPath(fileName);

            // save in database
            certificateRepository.save(certificate);

            // Save certificate in the S3 bucket
            //  s3Service.uploadFile(document, fileName);

            return "redirect:/personalpage";
        } catch (Exception e) {
            e.printStackTrace();
            ResponseEntity response = ResponseEntity.badRequest().body(FAILED_UPLOAD);
            model.addAttribute("upload_reponse", response);
            return "error";
        }
    }
}

