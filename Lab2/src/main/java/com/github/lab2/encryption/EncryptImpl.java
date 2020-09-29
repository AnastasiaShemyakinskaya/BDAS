package com.github.lab2.encryption;

import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.operator.OutputEncryptor;

import java.security.cert.X509Certificate;


public class EncryptImpl implements Encrypt {

    @Override
    public byte[] encryptData(byte[] data, X509Certificate encryptionCertificate){
        try {
            byte[] encryptedData = null;
            if (null != data && null != encryptionCertificate) {
                CMSEnvelopedDataGenerator cmsEnvelopedDataGenerator
                        = new CMSEnvelopedDataGenerator();

                JceKeyTransRecipientInfoGenerator jceKey
                        = new JceKeyTransRecipientInfoGenerator(encryptionCertificate);
                cmsEnvelopedDataGenerator.addRecipientInfoGenerator(jceKey);
                CMSTypedData msg = new CMSProcessableByteArray(data);
                OutputEncryptor encryptor
                        = new JceCMSContentEncryptorBuilder(CMSAlgorithm.AES128_CBC)
                        .setProvider("BC").build();
                CMSEnvelopedData cmsEnvelopedData = cmsEnvelopedDataGenerator
                        .generate(msg, encryptor);
                encryptedData = cmsEnvelopedData.getEncoded();
            }
            return encryptedData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
