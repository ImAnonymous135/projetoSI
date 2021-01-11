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
import controller.encryptions.KeyStorage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.util.Scanner;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author joaob
 */
public class Controller {

    private String nomeApp;
    private String versao;
    private KeyPair kp;
    private KeyPair kp1;

    public Controller(String nomeApp, String versao) {
        this.nomeApp = nomeApp;
        this.versao = versao;
        kp = KeyStorage.getKeys("appKeys.jks", "123456", "chave");
        kp1 = KeyStorage.getKeys("serviceKeys.jks", "123456", "chave");
    }

    public boolean isRegistered() throws Exception {
        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        String json = gson.toJson(c.decriptar("license.txt", (Key) kp.getPrivate()));

        json = json.substring(1, json.length() - 1);
        json = json.replaceAll("\\\\", "");
        Data data = gson.fromJson(json, Data.class);
        License license = data.getLicence();
        License license2 = new License("compare","compare","compare");
        
        if (isDataValid(license) + isUserValid(license, license2) + isSystemValid(license, license2) >= 3) {
            return false;
        }
        return true;
    }

    public boolean startRegistration() throws Exception {

        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        System.out.println("Digite o seu email: ");
        License license = new License(new Scanner(System.in).nextLine(), this.nomeApp, this.versao);
        String json = gson.toJson(new Data(license, AssinaturaDigital.sign(gson.toJson(license)), Certificado.getCertificado()));
        json = json.replaceAll("\\\\", "");
        //System.out.println(json);

        c.encriptar(json, (Key) kp1.getPublic());

        return false;
    }

    public void showLicenseInfo() throws Exception {
        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        String json = gson.toJson(c.decriptar("license.txt", (Key) kp1.getPrivate()));

        System.out.println(json);
        json = json.substring(1, json.length() - 1);
        json = json.replaceAll("\\\\", "");
        System.out.println(json);
        Data data = gson.fromJson(json, Data.class);

        System.out.println("Informação sobre aplicação");
        System.out.println("Nome da aplicação: " + data.getLicence().getAppName());
        System.out.println("Versão: " + data.getLicence().getAppVersion());
        System.out.println("Hash da aplicação: " + data.getLicence().getFileHash());
        System.out.println("Hash da biblioteca: " + data.getLicence().getLibrabryFileHash());
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println("Informação sobre o utilizador");
        System.out.println("Nome do utilizador: " + data.getLicence().getUserName());
        System.out.println("Email do utilizador: " + data.getLicence().getUserMail());
        System.out.println("Número de identificação: " + data.getLicence().getUserId());
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println("Informação sobre o sistema");
        System.out.println("Id do sistema operativo: " + data.getLicence().getSystemOsId());
        System.out.println("MAC address: " + data.getLicence().getSystemMac());
        System.out.println("Nome do CPU: " + data.getLicence().getSystemCpuName());
        System.out.println("Identificação do CPU: " + data.getLicence().getSystemCpuId());
        System.out.println("Discos do sistema: " + data.getLicence().getSystemHardDrivesId());
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println("Informação sobre o intervalo temporal de validade da licença");
        System.out.println("Data de começo: " + data.getLicence().getStartDate());
        System.out.println("Data de validade: " + data.getLicence().getExpirationDate());
    }

    //---------------------------------
    //Private methods
    //---------------------------------
    /*private License getLicense() {

        //Objetos
        Gson json = new Gson();
        File file = new File("license.txt");
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        CifraHibrida c = new CifraHibrida();
        String pvtKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";

        //Ler linha
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }

        return json.fromJson(decryptJson(), License.class);
    }*/
    private int isUserValid(License licenseStored, License licenseCurrent) {

        int isValid = 0;

        if (!licenseStored.getUserId().equals(licenseCurrent.getUserId())) {
            return isValid += 4;
        }
        if (!licenseStored.getUserName().equals(licenseCurrent.getUserName())) {
            return isValid += 4;
        }
        if (!licenseStored.getUserCertificate().equals(licenseCurrent.getUserCertificate())) {
            return isValid += 4;
        }
        return isValid;
    }
    
    private int isDataValid(License licenseStored) {
        
        int isValid = 0;
        
        if (LocalDateTime.now().isBefore(licenseStored.getExpirationDate())) {
            return isValid += 4;
        }
        return isValid;
    }
    
    private int isSystemValid(License licenseStored, License licenseCurrent) {
        
        int isValid = 0;
        
        if (!licenseStored.getSystemCpuId().equals(licenseCurrent.getSystemCpuId())) {
            isValid++;
        }
        if (!licenseStored.getSystemMac().equals(licenseCurrent.getSystemMac())) {
            isValid++;
        }
        if (!licenseStored.getSystemOsId().equals(licenseCurrent.getSystemOsId())) {
            isValid++;
        }
        if (isValid >= 3) {
            return isValid;
        }
       if (!licenseStored.getSystemHardDrivesId().equals(licenseCurrent.getSystemHardDrivesId())) {
            isValid++;
        }
        return isValid;
    }
}
