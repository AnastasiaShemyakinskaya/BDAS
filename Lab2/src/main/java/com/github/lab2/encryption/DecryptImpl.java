package com.github.lab2.encryption;

import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.KeyTransRecipientInformation;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;

import java.security.PrivateKey;
import java.util.Collection;

public class DecryptImpl implements Decrypt {
    @Override
    public byte[] decryptData(byte[] encryptedData, PrivateKey decryptionKey) {
        byte[] decryptedData = null;
        if (null != encryptedData && null != decryptionKey) {
            CMSEnvelopedData envelopedData;
            try {
                envelopedData = new CMSEnvelopedData(encryptedData);
                Collection<RecipientInformation> recipients
                        = envelopedData.getRecipientInfos().getRecipients();
                KeyTransRecipientInformation recipientInfo
                        = (KeyTransRecipientInformation) recipients.iterator().next();
                JceKeyTransRecipient recipient
                        = new JceKeyTransEnvelopedRecipient(decryptionKey);
                return recipientInfo.getContent(recipient);
            } catch (CMSException e) {
                e.printStackTrace();
            }
        }
        return decryptedData;
    }
}
