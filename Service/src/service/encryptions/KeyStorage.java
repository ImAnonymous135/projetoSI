/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.encryptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author joaob
 */
public class KeyStorage {

    private KeyStore store;
    private final String PATH = "notKeys.keystore";
    private String pass;

    public KeyStorage(String pass) {
        this.pass = pass;
        try {
            store = KeyStore.getInstance("JCEKS");
            store.load(null, this.pass.toCharArray());
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        return generator.generateKey();
    }

    public void newKey(String alias) {

        try {
            store.setKeyEntry(alias, generateKey(), pass.toCharArray(), null);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void storeKeys() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(PATH));
            store.store(outputStream, pass.toCharArray());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SecretKey loadKey(String alias) {

        try {
            InputStream inputStream = new FileInputStream(PATH);
            store.load(inputStream, pass.toCharArray());

            return (SecretKey) store.getKey(alias, pass.toCharArray());
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public String keyString(String alias) {
        return new String(loadKey(alias).getEncoded());
    }
}
