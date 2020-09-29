package com.github.lab2.encryption;

import java.security.cert.X509Certificate;

public interface Encrypt {
    byte[] encryptData(byte[] data, X509Certificate encryptionCertificate);
}
