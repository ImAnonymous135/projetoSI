/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author joaob
 */
public class Controller {
    License l = new License();
    private String nomeApp;
    private String versao;
    private License license;

    public Controller(String nomeApp, String versao) {
        this.nomeApp = nomeApp;
        this.versao = versao;
        this.license = new License();
    }
    
    public boolean isRegistered() throws IOException{
        Path path = Paths.get(System.getProperty("user.dir") + "/..").toRealPath();
        new File(path+"/informacao/nomeApp").mkdirs();
        return false;
    }
    
    public boolean startRegistration(){
        
        return false;
    }
    
    public void showLicenseInfo(){
    }
    
    
    //---------------------------------
    //Private methods
    //---------------------------------
    
    private void jsonEncrypt() {
        
        Gson gson = new Gson();
        String json = gson.toJson(license);
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(json.getBytes());
            String stringHash = new String(messageDigest.digest());
            System.out.println(stringHash);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
