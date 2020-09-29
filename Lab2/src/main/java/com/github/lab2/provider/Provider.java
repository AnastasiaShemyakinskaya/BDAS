package com.github.lab2.provider;

import com.github.lab2.encryption.Decrypt;
import com.github.lab2.encryption.DecryptImpl;
import com.github.lab2.encryption.Encrypt;
import com.github.lab2.encryption.EncryptImpl;
import com.github.lab2.sign.Signer;
import com.github.lab2.sign.SignerImpl;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class Provider {

    @Bean
    public X509Certificate x509Certificate () {
        try {
            Security.addProvider(new BouncyCastleProvider());
            CertificateFactory certFactory=CertificateFactory
                    .getInstance("X.509", "BC");
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream file = classLoader.getResourceAsStream("public.cer");

            return (X509Certificate) certFactory
                    .generateCertificate(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Bean
    public PrivateKey privateKey () {
        try {
            Security.addProvider(new BouncyCastleProvider());
            char[] keystorePassword = "password".toCharArray();
            char[] keyPassword = "password".toCharArray();

            KeyStore keystore = KeyStore.getInstance("PKCS12");
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream file = classLoader.getResourceAsStream("private.p12");

            keystore.load(file, keystorePassword);
            return (PrivateKey) keystore.getKey("baeldung",
                    keyPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Decrypt decrypt() {
        return new DecryptImpl();
    }

    @Bean
    public Encrypt encrypt() {
        return new EncryptImpl();
    }

    @Bean
    public Signer signer() {
        return new SignerImpl();
    }
}
