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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import service.encryptions.CifraHibrida;
import service.encryptions.Hash;

/**
 *
 * @author joaob
 */
public class Controller {
    
    public boolean isLicenseLegit(String path) {
        
        path = "licences/"+path;
        
        String jsonHash = getJsonHash(path);
        String json = getDecryptedJson(path);
        
        System.out.println(Hash.jsonHash(json)+" -> Licença");
        
        return jsonHash.equals(Hash.jsonHash(json));
    }
    
    private String getDecryptedJson(String path) {
        
        try {
            String pvtKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
            
            CifraHibrida c = new CifraHibrida();
            System.out.println(c.decriptar(path+"/license.txt", pvtKey)+"-> licença encriptado");
            return c.decriptar(path+"/license.txt", pvtKey);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Ocorreu um erro ao decriptar.";
    }

    /*private String getEncryptedJson() {

        try {
            String folder = new File("requests/").list()[0];
            File license = new File("requests/" + folder + "/license.txt");

            StringBuilder builder = new StringBuilder();
            Scanner scan = new Scanner(license);

            while (scan.hasNextLine()) {
                builder.append(scan.nextLine());
            }
            return builder.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Erro ao buscar Json Encriptado.";
    }*/

    private String getJsonHash(String path) {

        try {
            File hash = new File(path+"/hash.txt");

            StringBuilder builder = new StringBuilder();
            Scanner scan = new Scanner(hash);

            while (scan.hasNextLine()) {
                builder.append(scan.nextLine());
            }
            System.out.println(builder.toString()+"-> Hash");
            return builder.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Erro ao buscar hash do Json.";
    }
}
