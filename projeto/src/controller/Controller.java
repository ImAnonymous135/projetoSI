/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author joaob
 */
public class Controller {
    private String nomeApp;
    private String versao;

    public Controller(String nomeApp, String versao) {
        this.nomeApp = nomeApp;
        this.versao = versao;
    }
    
    public boolean isRegistered(){
        return false;
    }
    
    public boolean startRegistration(){
        return false;
    }
    
    public void showLicenseInfo(){
    }
}
