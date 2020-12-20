/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaob
 */
public class License {

    //User Info
    private String userName;
    private String userMail;
    private String userId;
    private String userCertificate;

    //System Info
    private String systemCpuName;
    private String systemCpuId;
    private String systemMac;
    private String systemBios;

    //App Info
    private String appName;
    private String appVersion;
    private String fileHash;
    private String librabryFileHash;

    //Time Info
    private String startDate;
    private String expirationDate;

    public License(String userName, String userMail, String userId, String userCertificate, String appName, String appVersion, String fileHash, String librabryFileHash) {
        this.userName = userName;
        this.userMail = userMail;
        this.userId = userId;
        this.userCertificate = userCertificate;
        this.appName = appName;
        this.appVersion = appVersion;
        this.fileHash = fileHash;
        this.librabryFileHash = librabryFileHash;

        //auto
        //this.systemCpuName = ;
        //this.systemCpuId = ;
        this.systemMac = getMac();
        //this.systemBios = ;
        this.startDate = getDate();
        this.expirationDate = getExpDate();
    }

    private String getMac() {

        try {
            InetAddress ip;
            
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            
            byte[] mac = network.getHardwareAddress();
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            
            return sb.toString();
        } catch (UnknownHostException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Erro";
    }

    private String getDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.toString();
    }

    private String getExpDate() {
        LocalDateTime now = LocalDateTime.now();
        now.plusYears(1).toString();
        return now.toString();
    }
}
