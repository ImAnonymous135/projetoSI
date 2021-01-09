/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import controller.encryptions.CifraHibrida;
import controller.encryptions.Hash;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        try {
            Controller c = new Controller("Jose", "20");
            c.startRegistration();
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
