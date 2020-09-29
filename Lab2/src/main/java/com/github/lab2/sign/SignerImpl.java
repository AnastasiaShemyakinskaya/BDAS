package com.github.lab2.sign;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SignerImpl implements Signer {
    @Override
    public byte[] signData(byte[] data, @Qualifier("x509Certificate") X509Certificate signingCertificate, @Qualifier("privateKey") PrivateKey signingKey) {
        byte[] signedMessage = null;
        try {
            List<X509Certificate> certList = new ArrayList<X509Certificate>();
            CMSTypedData cmsData= new CMSProcessableByteArray(data);
            certList.add(signingCertificate);
            Store certs = new JcaCertStore(certList);
            CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();
            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(signingKey);
            cmsGenerator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                    new JcaDigestCalculatorProviderBuilder().setProvider("BC")
                            .build()).build(contentSigner, signingCertificate));
            cmsGenerator.addCertificates(certs);

            CMSSignedData cms = cmsGenerator.generate(cmsData, true);
            signedMessage = cms.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedMessage;
    }

    @Override
    public boolean verifySignedData(byte[] signedData) {
        try {
            ByteArrayInputStream inputStream
                    = new ByteArrayInputStream(signedData);
            ASN1InputStream asnInputStream = new ASN1InputStream(inputStream);
            CMSSignedData cmsSignedData = new CMSSignedData(
                    ContentInfo.getInstance(asnInputStream.readObject()));
            SignerInformationStore signers
                    = cmsSignedData.getSignerInfos();
            SignerInformation signer = signers.getSigners().iterator().next();
            Collection<X509CertificateHolder> certCollection
                    = cmsSignedData.getCertificates().getMatches(signer.getSID());
            X509CertificateHolder certHolder = certCollection.iterator().next();

            return signer.verify(new JcaSimpleSignerInfoVerifierBuilder()
                    .build(certHolder));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
