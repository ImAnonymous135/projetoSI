/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;

/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SigarException {
        // TODO code application logic here

        System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
        System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
        System.out.println(System.getenv("PROCESSOR_ARCHITEW6432"));
        System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));

        
        
    }
}
