/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import service.encryptions.AssinaturaDigital;
import service.encryptions.CifraHibrida;
import service.encryptions.Hash;
import service.encryptions.KeyStorage;

/**
 *
 * @author joaob
 */
public class Controller {

    private KeyPair kp;
    private KeyPair kp1;

    public boolean isLicenseLegit(String path) throws Exception {
        
        kp = KeyStorage.getKeys("appKeys.jks", "123456", "chave");
        kp1 = KeyStorage.getKeys("serviceKeys.jks", "123456", "chave");
        
        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson(); 
        String json = gson.toJson(c.decriptar("license.txt", kp1.getPrivate()));
         
        json = json.substring(1, json.length() - 1); 
        json = json.replaceAll("\\\\", ""); 
        //System.out.println(json); 
        Data data = gson.fromJson(json, Data.class); 
        String license = gson.toJson(data.getLicence()); 
        //System.out.println(license); 
        if(Certificado.verificar(data.getCertificate())){ 
            //System.out.println(Arrays.toString(data.getSignature())); 
            if(AssinaturaDigital.verificar(data.getSignature(),license ,data.getCertificate())){ 
                System.out.println("Licença Aprovada!!"); 
                return true;
            }else{ 
                System.out.println("Assinatura Inválida!!"); 
            } 
        }else{ 
            System.out.println("Certificado Invalido!!"); 
        } 
         
        return false;
    }
    
    public void criarFicheiro(){
        kp = KeyStorage.getKeys("appKeys.jks", "123456", "chave");
        kp1 = KeyStorage.getKeys("serviceKeys.jks", "123456", "chave");
        
        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        System.out.println("Digite o seu email: ");
        License license = new License(new Scanner(System.in).nextLine(), this.nomeApp, this.versao);
        String json = gson.toJson(new Data(license, AssinaturaDigital.sign(gson.toJson(license)), Certificado.getCertificado()));
        json = json.replaceAll("\\\\", "");
        //System.out.println(json);

        c.encriptar(json, (Key) kp1.getPublic());
    }
}
