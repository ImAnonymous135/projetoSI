/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import javax.security.auth.callback.CallbackHandler;

/**
 *
 * @author josea
 */
public class AssinaturaDigital {

    public static byte[] sign(String hash) {
        try {
            Provider[] provs = Security.getProviders();
            for (int i = 0; i < provs.length; i++) {
                System.out.println(i + " - Nome do provider: " + provs[i].getName());
            }

            Provider p = provs[provs.length - 1];
            Security.addProvider(p);
            CallbackHandler cmdLineHdlr = new com.sun.security.auth.callback.TextCallbackHandler();
            KeyStore.Builder builder = KeyStore.Builder.newInstance("PKCS11", p,
                    new KeyStore.CallbackHandlerProtection(cmdLineHdlr));
            KeyStore ks = builder.getKeyStore();
            String assinaturaCertifLabel = "CITIZEN SIGNATURE CERTIFICATE";
            Key key = ks.getKey(assinaturaCertifLabel, null);
            Signature sig = Signature.getInstance("SHA1withRSA", p);
            sig.initSign((PrivateKey) key);
            sig.update(hash.getBytes());
            byte[] signedHash = sig.sign();
            return signedHash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
