/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Scanner;

/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Controller c = new Controller();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Nome da aplicação para testar:");
        String path = sc.nextLine();
        
        System.out.println(c.isLicenseLegit(path));
        
    }
}
