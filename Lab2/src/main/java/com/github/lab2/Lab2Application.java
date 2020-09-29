package com.github.lab2;

import com.github.lab2.encryption.Decrypt;
import com.github.lab2.encryption.Encrypt;
import com.github.lab2.sign.Signer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

@SpringBootApplication
public class Lab2Application {

    @Autowired
    private Signer signer;

    @Autowired
    private Encrypt encrypt;

    @Autowired
    private Decrypt decrypt;

    @Autowired
    private X509Certificate certificate;

    @Autowired
    private PrivateKey privateKey;

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String secretMessage = "My password is 123456Seven";
            System.out.println("Original Message : " + secretMessage);
            byte[] stringToEncrypt = secretMessage.getBytes();
            byte[] encryptedData = encrypt.encryptData(stringToEncrypt, certificate);
            System.out.println("Encrypted Message : " + new String(encryptedData));
            byte[] rawData = decrypt.decryptData(encryptedData, privateKey);
            String decryptedMessage = new String(rawData);
            System.out.println("Decrypted Message : " + decryptedMessage);

            byte[] signedData = signer.signData(rawData, certificate, privateKey);
            Boolean check = signer.verifySignedData(signedData);
            System.out.println(check);
        };
    }
}
