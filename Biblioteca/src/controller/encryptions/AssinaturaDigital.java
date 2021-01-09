/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.encryptions;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import javax.security.auth.callback.CallbackHandler;

/**
 *
 * @author josea
 */
public class AssinaturaDigital {

    public static byte[] sign(String hash) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, SignatureException {
        Provider ccProvider = Security.getProvider("SunPKCS11-CartaoCidadao");
        KeyStore ks = KeyStore.getInstance("PKCS11", ccProvider);
        ks.load(null, null);
        
        Certificate t = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");
        PrivateKey pk = (PrivateKey) ks.getKey("CITIZEN AUTHENTICATION CERTIFICATE", null);
        Signature sig = Signature.getInstance("SHA256withRSA", ccProvider);

        sig.initSign(pk);

        sig.update(hash.getBytes());

        byte[] signa = sig.sign();
        return signa;
    }
    
    public static boolean verificar(String hash,String info) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, SignatureException {
        Provider ccProvider = Security.getProvider("SunPKCS11-CartaoCidadao");
        KeyStore ks = KeyStore.getInstance("PKCS11", ccProvider);
        ks.load(null, null);
        
        Certificate t = ks.getCertificate("CITIZEN AUTHENTICATION CERTIFICATE");PublicKey pk1 = t.getPublicKey();
        Signature mysignature = Signature.getInstance("SHA256withRSA");
        mysignature.initVerify(pk1);
        
        mysignature.update(info.getBytes());

        boolean verifies = mysignature.verify(hash.getBytes());
        
        return verifies;
    }
}