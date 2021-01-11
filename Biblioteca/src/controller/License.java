/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private String systemOsId;
    private List<String> systemHardDrivesId;

    //App Info
    private String appName;
    private String appVersion;
    private String fileHash;
    private String librabryFileHash;

    //Time Info
    private String startDate;
    private LocalDateTime expirationDate;

    public License(String userMail, String appName, String appVersion) {
        
        this.userMail = userMail;
        this.appName = appName;
        this.appVersion = appVersion;
        //this.fileHash = fileHash;
        //this.librabryFileHash = librabryFileHash;

        this.systemCpuName = getCpuName();
        this.systemCpuId = System.getenv("PROCESSOR_IDENTIFIER");
        this.systemMac = getMac();
        this.systemOsId = getOsSerial();
        this.systemHardDrivesId = getSystemIdList();
        this.startDate = getDate();
        this.expirationDate = getExpDate(12);
    }

    //---------------------------------
    //Private methods
    //---------------------------------
    private String getOsSerial() {

        String line = "";

        try {
            Process p = Runtime.getRuntime().exec("cmd /c vol C:");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = reader.readLine()) != null) {
                if (line.contains("Serial Number")) {
                    line = line.replace("Volume Serial Number is ", "");
                    line = line.trim();
                    return line;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        }

        return line;
    }

    private List<String> getSystemIdList() {

        List<String> systemId = new ArrayList<String>();

        try {
            Process p = Runtime.getRuntime().exec("cmd /c wmic diskdrive get serialNumber");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 1 && !line.contains("SerialNumber")) {
                    line = line.trim();
                    systemId.add(line);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        }
        return systemId;
    }

    private String getCpuName() {

        String cpuName = "";

        try {
            Process p = Runtime.getRuntime().exec("cmd /c wmic cpu get name");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > 1 && (line.contains("AMD") || line.contains("Intel"))) {
                    line = line.trim();
                    cpuName = line;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cpuName;
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

    private LocalDateTime getExpDate(int months) {
        LocalDateTime now = LocalDateTime.now();
        now = now.plusMonths(months);
        return now;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
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

    public String getUserCertificate() {
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

    public String getFileHash() {
        return fileHash;
    }

    public String getLibrabryFileHash() {
        return librabryFileHash;
    }

    public String getStartDate() {
        return startDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

}
