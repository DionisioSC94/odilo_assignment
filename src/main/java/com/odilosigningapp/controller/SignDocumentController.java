package com.odilosigningapp.controller;

import com.odilosigningapp.Models.Certificate;
import com.odilosigningapp.Models.DocumentToSign;
import com.odilosigningapp.Models.User;
import com.odilosigningapp.service.CertificateService;
import com.odilosigningapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InvalidObjectException;

@Controller
public class SignDocumentController {
    @Autowired
    UserService userService;

    @Autowired
    CertificateService certificateService;

    @PostMapping("/upload_document_to_sign")
    public String uploadDocumentToSign(Model model,
                                       Authentication authentication,
                                       @RequestParam("documentToSign") MultipartFile document,
                                       @RequestParam("certificatePassword") String password)
    {
        // Get authenticated user and certificate
        User user = userService.findAuthenticatedUser(authentication);
        Certificate certificate = certificateService.findCertificateByUserId(user.getId());

        // Check if the uploaded document and password are valid
        if (document.isEmpty() || password.isEmpty() || certificate == null) {
            // Handle invalid input
            return "redirect:/error";
        }

        // Hash password input by user for security
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //TODO: debug this check, since currently not working.
        if (BCrypt.checkpw(hashedPassword, certificate.getPassword())) {
            // Add the document to the model
            DocumentToSign documentToSign = new DocumentToSign();
            documentToSign.setContent(document);
            model.addAttribute("documentToSign", documentToSign);

            // AddInputPassword
            model.addAttribute("userInputPasswordCertificate", hashedPassword);
        }
        return "redirect:/personalpage";
    }

    @PostMapping("/firma")
    public String firma(@RequestBody String data) {
        String firma = "#firma#";
        return firma + data;
    }


    // Alternative implementation to sign from backend
    @PostMapping("/firmaBackEnd")
    public String firmaFromWeb(@RequestBody DocumentToSign documentToSign,
                        Authentication authentication,
                        Model model) throws InvalidObjectException {

        // Get authenticated user and certificate (not taken from model for security)
        User user = userService.findAuthenticatedUser(authentication);
        Certificate certificate = certificateService.findCertificateByUserId(user.getId());

        // get hashedPassword from user
        String userPassword = (String) model.getAttribute("userInputPasswordCertificate");

        String data = documentToSign.sign(certificate, userPassword);
        String firma = "#firma#";
        return firma + data;
    }
}
