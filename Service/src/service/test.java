/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.encryptions.KeyStorage;


/**
 *
 * @author joaob
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Controller c = new Controller();
        System.out.println(c.isLicenseLegit("aprovar/license.txt"));
    }
}
