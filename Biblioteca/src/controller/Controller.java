/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.encryptions.CifraHibrida;
import controller.encryptions.AssinaturaDigital;
import com.google.gson.Gson;
import controller.encryptions.Hash;
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
    private CifraHibrida c = new CifraHibrida();
    private AssinaturaDigital a = new AssinaturaDigital();

    public Controller(String nomeApp, String versao) {
        this.nomeApp = nomeApp;
        this.versao = versao;
        this.license = new License();
    }

    public boolean isRegistered() throws IOException {
        
        return false;
    }

    public boolean startRegistration() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(license);
        c.encriptar(json, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB");
        a.sign(json);
        return false;
    }

    public void showLicenseInfo() {

    }

    public void setMail(String mail) {
        this.license.setUserMail(mail);
    }

    //---------------------------------
    //Private methods
    //---------------------------------
}
