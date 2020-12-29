/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

       License l = new License();
       
        System.out.println(l.getSystemId());
        
        //System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
        //System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
        //System.out.println(System.getenv("PROCESSOR_ARCHITEW6432"));
        //System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
    }
}
