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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaob
 */
public class KeyStorage {

    public static Key getPublicKey(String pass, String path) {

        try {
            FileInputStream fi = new FileInputStream(new File("keys/"+path));
            ObjectInputStream oi = new ObjectInputStream(fi);
            
            String storedPass = (String) oi.readObject();
            
            if (storedPass.equals(Base64.getEncoder().encodeToString(Hash.getStringHash(pass)))) {
                System.out.println("Right password");
                Key k = (Key) oi.readObject();
                return k;
            }
            System.out.println("Wrong password");
            fi.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void storePublicKey(Key key, String pass, String path) {

        ObjectOutputStream o = null;
        try {
            FileOutputStream f = new FileOutputStream(new File(path));
            o = new ObjectOutputStream(f);
            o.writeObject(Base64.getEncoder().encodeToString(Hash.getStringHash(pass)));
            o.writeObject(key);
            o.close();
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                o.close();
            } catch (IOException ex) {
                Logger.getLogger(KeyStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
