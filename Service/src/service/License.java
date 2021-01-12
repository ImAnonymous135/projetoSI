/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.encryptions.Hash;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaob
 */
public class License {

    private byte[] fileHash;
    private byte[] librabryFileHash;
    
    //User Info
    private String userName;
    private String userMail;
    private String userId;
    private byte[] userCertificate;

    //System Info
    private String systemCpuName;
    private String systemCpuId;
    private String systemMac;
    private String systemOsId;
    private List<String> systemHardDrivesId;

    //App Info
    private String appName;
    private String appVersion;
    

    //Time Info
    private String startDate;
    private String expirationDate;

    public License(String userMail, String appName, String appVersion) {
    }
    //---------------------------------
    //Getters
    //---------------------------------
    public String getSystemOsId() {
        return systemOsId;
    }

    public List<String> getSystemHardDrivesId() {
        return systemHardDrivesId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getUserCertificate() {
        return userCertificate;
    }

    public String getSystemCpuName() {
        return systemCpuName;
    }

    public String getSystemCpuId() {
        return systemCpuId;
    }

    public String getSystemMac() {
        return systemMac;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public byte[] getFileHash() {
        return fileHash;
    }

    public byte[] getLibrabryFileHash() {
        return librabryFileHash;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

}
