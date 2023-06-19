package com.odilosigningapp.Models;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.IOException;
import java.io.InvalidObjectException;

@AllArgsConstructor
public class DocumentToSign {
    private MultipartFile content;


    public String transformToBinary() throws InvalidObjectException {
        try {
            byte[] fileBytes = content.getBytes();
            StringBuilder binaryString = new StringBuilder();
            for (byte fileByte : fileBytes) {
                String byteString = Integer.toBinaryString(fileByte & 0xFF);
                String paddedByteString = String.format("%8s", byteString).replace(' ', '0');
                binaryString.append(paddedByteString);
            }
            return binaryString.toString();

        } catch (IOException e) {
            throw new InvalidObjectException("It was not possible to get the binary from the object");
        }
    }

    public String sign(Certificate certificate, String userInputPassword) throws InvalidObjectException {
        if ((BCrypt.checkpw(userInputPassword, certificate.getPassword()))) {
            String binaryRepresentation = transformToBinary();
            if (binaryRepresentation != null) {
                return "#firma#" + binaryRepresentation;
            }
        }
        return null;
    }

    // @AllArgsConstructor not working for some reason (java 1.8 probably)
    public MultipartFile getContent() {
        return content;
    }

    public void setContent(MultipartFile content) {
        this.content = content;
    }

}
