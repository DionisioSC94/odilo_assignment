package com.odilosigningapp.Models;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="certificates")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=true)
    private String password;

    @Column(nullable=false)
    private String path;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    /**
     * Authenticates the certificate by comparing the provided password with the stored encrypted hash.
     *
     * @param providedPassword the password provided by the user for authentication
     * @return true if the provided password matches the stored encrypted hash, false otherwise
     */
    public boolean authenticateCertificate(String providedPassword) {
            return BCrypt.checkpw(providedPassword, password);
    }

// Getters and Setters (Manually implemented due to lombok annotations not working)
    public Long getId() {
            return id;
    }
    public void setId(Long id) {
            this.id = id;
    }
    public String getPath() {
            return path;
    }
    public void setPath(String path) {
            this.path = path;
    }
    public User getUser() {
            return user;
    }
    public void setUser(User user) {
            this.user = user;
    }
    public void setPassword(String password) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            this.password = hashedPassword;
    }
}