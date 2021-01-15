/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.cert.Certificate;

/**
 *
 * @author josea
 */
public class Data {

    private License licence;
    private byte[] signature;
    private Certificate publicKeyCertificate;

    public Data(License licence, byte[] signature, Certificate certificate) {
        this.licence = licence;
        this.signature = signature;
        this.publicKeyCertificate = certificate;
    }

    public License getLicence() {
        return licence;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Certificate getPublicKeyCertificate() {
        return publicKeyCertificate;
    }
}
