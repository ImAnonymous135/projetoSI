/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.security.cert.Certificate;

/**
 *
 * @author josea
 */
public class Data {

    private License licence;
    private byte[] signature;
    private byte[] publicKeyCertificate;

    public Data(License licence, byte[] signature, byte[] certificate) {
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

    public byte[] getPublicKeyCertificate() {
        return publicKeyCertificate;
    }
}
