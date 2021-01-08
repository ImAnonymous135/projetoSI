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
import java.util.Scanner;

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

    public boolean isRegistered() throws IOException {
        License license = new License(new Scanner(System.in).nextLine(), this.nomeApp, this.versao);
        return false;
    }

    public boolean startRegistration() throws Exception {

        CifraHibrida c = new CifraHibrida();
        AssinaturaDigital a = new AssinaturaDigital();

        License license = new License(new Scanner(System.in).nextLine(), this.nomeApp, this.versao);

        Gson gson = new Gson();
        String json = gson.toJson(license);

        c.encriptar(json, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB");
        a.sign(json);

        return false;
    }

    public void showLicenseInfo() {
        try {
            if (isRegistered()) {
                System.out.println("Nome da aplicação: ");
            } else {
                System.out.println("Não possui licença válida.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //---------------------------------
    //Private methods
    //---------------------------------
    private License getLicense() {

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

        return c.decriptar("/license.txt", pvtKey);

        return json.fromJson(decryptJson(), License.class);
    }
}
