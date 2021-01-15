/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PKIXRevocationChecker;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Enumeration;

/**
 *
 * @author josea
 */
public class Certificado {

    //codigo tirado do guiao Assinatura Digital com o Cartão de Cidadão do elearning
    public static byte[] getCertificado() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");
        KeyStore ks;

        ks = KeyStore.getInstance("PKCS11", prov);
        ks.load(null, null);
        Enumeration<String> als = ks.aliases();
        Certificate t = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
        return t.getEncoded();
    }

    //codigo tirado do guiao Validação de Certificados em Java no elearning
    public static boolean verificar(byte[] certificado) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");

        KeyStore ks;

        ks = KeyStore.getInstance("PKCS11", prov);
        ks.load(null, null);
        Enumeration<String> als = ks.aliases();
        Certificate t = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

        InputStream in = new ByteArrayInputStream(certificado);

        Certificate certif = certFactory.generateCertificate(in);

        PKIXParameters par = new PKIXParameters(ks);
        for (TrustAnchor ta : par.getTrustAnchors()) {
            X509Certificate c = ta.getTrustedCert();
        }

        X509CertSelector cs = new X509CertSelector();
        cs.setCertificate((X509Certificate) certif);
        CertPathBuilder cpb = CertPathBuilder.getInstance("PKIX");
        PKIXBuilderParameters pkixBParams
                = new PKIXBuilderParameters(par.getTrustAnchors(), cs);
        pkixBParams.setRevocationEnabled(false);
        CollectionCertStoreParameters ccsp
                = new CollectionCertStoreParameters(Arrays.asList());
        CertStore store = CertStore.getInstance("Collection", ccsp);
        pkixBParams.addCertStore(store);
        CertPath cp = null;
        try {
            CertPathBuilderResult cpbr = cpb.build(pkixBParams);
            cp = cpbr.getCertPath();
        } catch (CertPathBuilderException ex) {
        }

        PKIXParameters pkixParams = new PKIXParameters(par.getTrustAnchors());
        CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
        pkixParams.setRevocationEnabled(false);
        Security.setProperty("ocsp.enable", "true");
        PKIXRevocationChecker rc = (PKIXRevocationChecker) cpv.getRevocationChecker();
        rc.setOptions(EnumSet.of(PKIXRevocationChecker.Option.SOFT_FAIL,
                PKIXRevocationChecker.Option.NO_FALLBACK));
        PKIXCertPathValidatorResult result = null;

        try {
            result = (PKIXCertPathValidatorResult) cpv.validate(cp, pkixParams);
            return true;
        } catch (CertPathValidatorException cpve) {
            return false;
        }
    }

    public static Certificate byteToCertificate(byte[] cer) throws CertificateException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        InputStream in = new ByteArrayInputStream(cer);
        Certificate certif = certFactory.generateCertificate(in);
        return certif;
    }
}
