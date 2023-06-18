package com.odilosigningapp.controller;

import com.odilosigningapp.Models.Certificate;
import com.odilosigningapp.dto.UserDto;
import com.odilosigningapp.repository.CertificateRepository;
import com.odilosigningapp.service.CertificateService;
import com.odilosigningapp.service.S3Service;
import com.odilosigningapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.odilosigningapp.Models.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
public class UserAuthenticationController {
    @Autowired
    UserService userService;

    @Autowired
    S3Service s3Service;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    CertificateService certificateService;

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/personalpage")
    public String showPersonalPage(Model model, Authentication authentication) {
        // Find the authenticated user
       User user = userService.findAuthenticatedUser(authentication);

        // Find the Certificate for the user. Null if not present
        Certificate userCertificate = certificateService.findCertificateByUserId(user.getId());

        // Add the user object and certificate to the model
        model.addAttribute("user", user);
        model.addAttribute("certificate", userCertificate);

        // Return the view name
        return "personalpage";
    }
}
