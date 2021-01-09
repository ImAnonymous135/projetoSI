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
public class Data {
    
    private License licence;
    private byte[] signature;
    private byte[] certificate;

    public Data(License licence, byte[] signature, byte[] certificate) {
        this.licence = licence;
        this.signature = signature;
        this.certificate = certificate;
    }
}
