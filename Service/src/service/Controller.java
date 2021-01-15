/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import service.encryptions.AssinaturaDigital;
import service.encryptions.CifraHibrida;
import service.encryptions.KeyStorage;

/**
 *
 * @author joaob
 */
public class Controller {

    private KeyPair kpService;

    public Boolean isLicenseLegit(String path) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, SignatureException, FileNotFoundException, Exception {
        kpService = KeyStorage.getKeys("serviceKeys.jks", "123456", "chave");

        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        String json = gson.toJson(c.decriptar(path, kpService.getPrivate()));
        json = json.substring(1, json.length() - 1);
        json = json.replaceAll("\\\\", "");
        Data data = gson.fromJson(json, Data.class);
        String license = gson.toJson(data.getLicence());
        if (Certificado.verificar(data.getLicence().getUserCertificate())) {
            if (AssinaturaDigital.verificar(data.getSignature(), license, data.getLicence().getUserCertificate())) {
                criarFicheiro("licenca/" + data.getLicence().getAppName(), data.getLicence(), data.getPublicKeyCertificate());
                System.out.println("Licença Aprovada!!");
                return true;
            } else {
                System.out.println("Assinatura Inválida!!");
                return false;
            }
        } else {
            System.out.println("Certificado da chave publica do utilizador invalido!!");
            return false;
        }
    }

    private void criarFicheiro(String path, License license, byte[] cer) throws Exception {

        CifraHibrida c = new CifraHibrida();
        Gson gson = new Gson();
        String json = gson.toJson(new Data(license, AssinaturaDigital.sign(gson.toJson(license)), KeyStorage.getPublicKeyCertificateFromStorage("serviceKeys.jks", "123456", "chave").getEncoded()));
        json = json.replaceAll("\\\\", "");

        c.encriptar(path, json, (Key) Certificado.byteToCertificate(cer).getPublicKey());
    }
}
