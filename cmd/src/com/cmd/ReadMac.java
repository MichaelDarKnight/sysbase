package com.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadMac {
    public static String getAixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("lscfg -vp -l ent0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line:" + line);
                index = line.indexOf("Network Address.............");
                if (index >= 0) {
                    mac = line.substring(index + "Network Address.............".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    public static String getMACAddress() {
        String os = getOSName();
        String mac = "";
        if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
        } else {
            mac = getUnixMACAddress();
        }

        return mac;
    }

    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.toLowerCase().indexOf("physical address") != -1) {
                    index = line.toLowerCase().indexOf("physical address");
                } else if (line.toLowerCase().indexOf("物理地址") != -1) {
                    index = line.toLowerCase().indexOf("物理地址");
                }
                if (index >= 0) {
                    index = line.indexOf(":");
                    if (index < 0)
                        break;
                    mac = line.substring(index + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    public static void main(String[] argc) {
        String os = getOSName();
        System.out.println(os);
        if (os.startsWith("windows")) {
            String mac = getWindowsMACAddress();
            System.out.println(mac);
        } else if (os.startsWith("aix")) {
            String mac = getAixMACAddress();
            System.out.println(mac);
        } else {
            String mac = getUnixMACAddress();
            System.out.println(mac);
        }
    }
}