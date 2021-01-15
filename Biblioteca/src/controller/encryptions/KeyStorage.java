/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.encryptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaob
 */
public class KeyStorage {

    public static void newCertificateFile(String path, String password, String alias, String cerName) {

        try {
            FileOutputStream fos = new FileOutputStream(new File(cerName));
            fos.write(getPublicKeyCertificateFromStorage(path, password, alias).getEncoded());
            fos.close();
        } catch (Exception e) {
            System.out.println("erro a escrever ficheiro: " + e);
        }
    }

    public static Certificate getPublicKeyCertificate(String path) {

        FileInputStream is = null;
        try {
            CertificateFactory fac = CertificateFactory.getInstance("X509");
            is = new FileInputStream(path);
            return (X509Certificate) fac.generateCertificate(is);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static Certificate getPublicKeyCertificateFromStorage(String path, String password, String alias) {
        try {
            FileInputStream is = new FileInputStream("keys/" + path);

            KeyStore keystore = KeyStore.getInstance("jks");
            keystore.load(is, password.toCharArray());

            Key key = keystore.getKey(alias, password.toCharArray());

            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);

                return keystore.getCertificate(alias);
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static KeyPair getKeys(String path, String password, String alias) {

        try {
            FileInputStream is = new FileInputStream("keys/" + path);

            KeyStore keystore = KeyStore.getInstance("jks");
            keystore.load(is, password.toCharArray());

            Key key = keystore.getKey(alias, password.toCharArray());

            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);

                // Get public key
                PublicKey publicKey = cert.getPublicKey();

                //System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
                //System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
