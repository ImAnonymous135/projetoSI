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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaob
 */
public class  Controller {

    /*public static void readRequests() {

        //1 Ficheiro
        try {
            

        } catch (Exception e) {
        }

    }*/

    private void jsonDecrypt(File json) {

        String folder = new File("requests/").list()[0];
        File license = new File("requests/" + folder+"/license.txt");
        Gson gson = new Gson();

        //License license = gson.fromJson(json, License.class);

    }

    public static String jsonHash() {

        try {
            String folder = new File("requests/").list()[0];
            File hash = new File("requests/" + folder+"/hash.txt");
            
            StringBuilder builder = new StringBuilder();
            Scanner scan = new Scanner(hash);
            
            while (scan.hasNextLine()) {
                builder.append(scan.nextLine());
            }
            return builder.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Ocorreu um erro";
    }
}
