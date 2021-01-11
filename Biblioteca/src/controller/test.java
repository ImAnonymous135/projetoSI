/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.encryptions.CifraHibrida;
import controller.encryptions.Hash;
import controller.encryptions.KeyStorage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, Exception {
<<<<<<< Updated upstream
        Controller c = new Controller("jo","se");
        c.startRegistration();
        //c.showLicenseInfo();
=======
        //Controller c = new Controller("jo","se");
        //c.startRegistration();
        //c.showLicenseInfo();
        
        System.out.println("Hello world");
        
>>>>>>> Stashed changes
    }
}
