/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.encryptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
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
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
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
    
    public static boolean verificar(byte[] hash, String info) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, SignatureException {
        KeyPair kpService = KeyStorage.getKeys("serviceKeys.jks", "123456", "chave");
        PublicKey pk = kpService.getPublic();
        
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(pk);
        
        signature.update(info.getBytes());

        boolean verifies = signature.verify(hash);
        
        return verifies;
    }
}