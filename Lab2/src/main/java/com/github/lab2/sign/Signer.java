package com.github.lab2.sign;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface Signer {
    byte[] signData(byte[] data, X509Certificate signingCertificate, PrivateKey signingKey);
    boolean verifySignedData(byte[] signedData);
}
