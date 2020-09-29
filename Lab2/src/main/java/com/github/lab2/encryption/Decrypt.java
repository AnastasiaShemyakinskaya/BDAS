package com.github.lab2.encryption;

import java.security.PrivateKey;

public interface Decrypt {
    byte[] decryptData(byte[] encryptedData, PrivateKey decryptionKey);
}
