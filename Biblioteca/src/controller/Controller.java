/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.encryptions.CifraHibrida;
import controller.encryptions.AssinaturaDigital;
import com.google.gson.Gson;
import controller.encryptions.KeyStorage;
import java.io.FileNotFoundException;
import java.security.Key;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author joaob
 */
public class Controller {

    private String nomeApp;
    private String versao;
    private KeyPair kpApp;
    private KeyPair kpService;
    
    public Controller(String nomeApp, String versao) {
        this.nomeApp = nomeApp;
        this.versao = versao;
        kpApp = KeyStorage.getKeys("appKeys.jks", "123456", "chave");
        kpService = KeyStorage.getKeys("serviceKeys.jks", "123456", "chave");
    }

    public boolean isRegistered() throws FileNotFoundException,Exception {
        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        String json = gson.toJson(c.decriptar("licenca/license.txt", (Key) kpApp.getPrivate()));

        json = json.substring(1, json.length() - 1);
        json = json.replaceAll("\\\\", "");
        Data data = gson.fromJson(json, Data.class);
        License license = data.getLicence();
        if (Certificado.verificar(data.getLicence().getUserCertificate())) {
            if (AssinaturaDigital.verificar(data.getSignature(), gson.toJson(data.getLicence()))) {
                License license2 = new License("compare", "compare", "compare");
                if (isDataValid(license) + isUserValid(license, license2) + isSystemValid(license, license2) >= 3) {
                    return false;
                }
            } else {
                System.out.println("Assinatura Inválida!!");
            }
        } else {
            System.out.println("Certificado Invalido!!");
        }

        return true;
    }

    public boolean startRegistration() throws Exception {

        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        System.out.println("Digite o seu email: ");
        License license = new License(new Scanner(System.in).nextLine(), this.nomeApp, this.versao);
        String json = gson.toJson(new Data(license, AssinaturaDigital.sign(gson.toJson(license)), null));
        json = json.replaceAll("\\\\", "");

        c.encriptar(json, (Key) kpService.getPublic());

        return false;
    }

    public void showLicenseInfo() throws Exception {
        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        String json = gson.toJson(c.decriptar("licenca/license.txt", (Key) kpApp.getPrivate()));

        //System.out.println(json);
        json = json.substring(1, json.length() - 1);
        json = json.replaceAll("\\\\", "");
        //System.out.println(json);
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
            System.out.println("a");
            return isValid += 4;
        }
        if (!licenseStored.getUserName().equals(licenseCurrent.getUserName())) {
            return isValid += 4;
        }
        if (!Arrays.equals(licenseCurrent.getUserCertificate(), licenseStored.getUserCertificate())) {
            return isValid += 4;
        }
        return isValid;
    }

    private int isDataValid(License licenseStored) {

        int isValid = 0;

        if (LocalDateTime.now().isAfter(LocalDateTime.parse(licenseStored.getExpirationDate()))) {
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
