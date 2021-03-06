/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.encryptions;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author josea
 */
public class Simetrica {

    public static byte[] encriptar(SecretKey secretkey, byte[] enc) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretkey);

        byte[] outputBytes = encryptCipher.doFinal(enc);

        return outputBytes;
    }

    public static byte[] decriptar(SecretKey secretkey, byte[] desenc) throws Exception {
        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretkey);
        byte[] outputBytes = decryptCipher.doFinal(desenc);
        return outputBytes;
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen;
        keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey key = keyGen.generateKey();
        return key;
    }
}
