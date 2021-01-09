/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import service.encryptions.KeyStorage;

/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        KeyStorage keys = new KeyStorage("pass");

        keys.newKey("private");
        keys.newKey("public");
        keys.storeKeys();
        
        //retorna Secretkey
        keys.loadKey("private");
        

        System.out.println(keys.keyString("private"));
        System.out.println(keys.keyString("public"));

    }
}
