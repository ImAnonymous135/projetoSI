/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
        Certificate t = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
        return t.getEncoded();
    }

    //codigo tirado do guiao Validação de Certificados em Java no elearning
    public static boolean verificar(byte[] certificado) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        Provider prov = Security.getProvider("SunPKCS11-CartaoCidadao");

        KeyStore ks;

        ks = KeyStore.getInstance("PKCS11", prov);
        ks.load(null, null);
        Certificate t = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

        InputStream in = new ByteArrayInputStream(certificado);

        // generate certificate (according to Java API)
        Certificate certif = certFactory.generateCertificate(in);

        PKIXParameters par = new PKIXParameters(ks);
        for (TrustAnchor ta : par.getTrustAnchors()) {
            X509Certificate c = ta.getTrustedCert();
        }

        //defines the end-user certificate as a selector
        X509CertSelector cs = new X509CertSelector();
        cs.setCertificate((X509Certificate) certif);
//Create an object to build the certification path
        CertPathBuilder cpb = CertPathBuilder.getInstance("PKIX");
//Define the parameters to buil the certification path and provide the Trust anchor
//certificates (trustAnchors) and the end user certificate (cs)
        PKIXBuilderParameters pkixBParams
                = new PKIXBuilderParameters(par.getTrustAnchors(), cs);
        pkixBParams.setRevocationEnabled(false); //No revocation check
//Provide the intermediate certificates (iCerts)
        CollectionCertStoreParameters ccsp
                = new CollectionCertStoreParameters(Arrays.asList());
        CertStore store = CertStore.getInstance("Collection", ccsp);
        pkixBParams.addCertStore(store);
//Build the certification path
        CertPath cp = null;
        try {
            CertPathBuilderResult cpbr = cpb.build(pkixBParams);
            cp = cpbr.getCertPath();
        } catch (CertPathBuilderException ex) {
            return false;
        }

        PKIXParameters pkixParams = new PKIXParameters(par.getTrustAnchors());
//Class that performs the certification path validation
        CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
//Disables the previous mechanism for revocation check (pre Java8)
        pkixParams.setRevocationEnabled(false);
//Enable OCSP verification
        Security.setProperty("ocsp.enable", "true");
//Instantiate a PKIXRevocationChecker class
        PKIXRevocationChecker rc = (PKIXRevocationChecker) cpv.getRevocationChecker();
//Configure to validate all certificates in chain using only OCSP
        rc.setOptions(EnumSet.of(PKIXRevocationChecker.Option.SOFT_FAIL,
                PKIXRevocationChecker.Option.NO_FALLBACK));
        PKIXCertPathValidatorResult result = null;

        try {
//Do the velidation
            result = (PKIXCertPathValidatorResult) cpv.validate(cp, pkixParams);
            return true;
        } catch (CertPathValidatorException cpve) {
            return false;
        }
    }
}
