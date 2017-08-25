package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class SystemUitls extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * System.out.println("==============================");
         * System.out.println("===========�ӿڵ��óɹ�==========="); // 1.01
         * getParameter(String strTextName) ��ȡ���ύ����Ϣ�� String strName =
         * request.getParameter("name"); System.out.println(strName + ">>>>>1");
         * // 1.02 getProtocol() ��ȡ�ͻ�ʹ�õ�Э�顣 String strProtocol =
         * request.getProtocol(); System.out.println(strProtocol + ">>>>>2"); //
         * 1.03 getServletPath() ��ȡ�ͻ��ύ��Ϣ��ҳ�档 String strServlet =
         * request.getServletPath(); System.out.println(strServlet + ">>>>>3");
         * // 1.04 getMethod() ��ȡ�ͻ��ύ��Ϣ�ķ�ʽ��get|post�� String strMethod =
         * request.getMethod(); System.out.println(strMethod + ">>>>>4"); //
         * 1.05 getHeade() ��ȡHTTPͷ�ļ��е�accept��accept-encoding��Host��ֵ�� String
         * strHeader = request.getHeader("accept"); System.out.println(strHeader
         * + ">>>>>5"); // 1.06 getRermoteAddr() ��ȡ�ͻ���IP��ַ�� String strIP =
         * request.getRemoteAddr(); System.out.println(strIP + ">>>>>6"); //
         * 1.07 getRemoteHost() ��ȡ�ͻ��������ơ� String clientName =
         * request.getRemoteHost(); System.out.println(clientName + ">>>>>7");
         * // 1.08 getServerName() ��ȡ���������ơ� String serverName =
         * request.getServerName(); System.out.println(serverName + ">>>>>8");
         * // 1.09 getServerPort() ��ȡ�������Ķ˿ںš� int serverPort =
         * request.getServerPort(); System.out.println(serverPort +
         * "getIpAddr"); // 1.10 getParameterNames() ��ȡ�ͻ����ύ�����в��������֡�
         * Enumeration enum1 = request.getParameterNames(); while
         * (enum1.hasMoreElements()) { String s = (String) enum1.nextElement();
         * System.out.println(s); } System.out.println("==============end===");
         * System.out.println(getIpAddr(request));
         * System.out.println(getRequestBrowserInfo(request));
         * System.out.println(getRequestSystemInfo(request));
         * System.out.println(getHostName(strIP));
         * System.out.println(getMacAddress(strIP));
         */
        request.setAttribute("context", JSONArray.fromObject(buiding()));
        request.getRequestDispatcher("/drag.jsp").forward(request, response);
        //  request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /** 
     * ��ȡ������IP 
     * ��һ�������ʹ��Request.getRemoteAddr()���ɣ����Ǿ���nginx�ȷ��������������������ʧЧ�� 
     *  
     * �������ȴ�Header�л�ȡX-Real-IP������������ٴ�X-Forwarded-For��õ�һ��IP(��,�ָ�)�� 
     * ����������������Request .getRemoteAddr()�� 
     * @param request 
     * @return 
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // ��η���������ж��IPֵ����һ��Ϊ��ʵIP��  
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /** 
     * ��ȡ�����ߵ�������汾 
     * @param request 
     * @return 
     */
    public static String getRequestBrowserInfo(HttpServletRequest request) {
        String browserVersion = null;
        String header = request.getHeader("user-agent");
        // System.out.println("=================" + header);
        if (header == null || header.equals("")) {
            return "";
        }
        if (header.indexOf("MSIE") > 0) {
            browserVersion = "IE";
        } else if (header.indexOf("like Gecko") != -1) {
            browserVersion = "IE";
        } else if (header.indexOf("Firefox") > 0) {
            browserVersion = "Firefox";
        } else if (header.indexOf("Chrome") > 0) {
            browserVersion = "Chrome";
        } else if (header.indexOf("Safari") > 0) {
            browserVersion = "Safari";
        } else if (header.indexOf("Camino") > 0) {
            browserVersion = "Camino";
        } else if (header.indexOf("Konqueror") > 0) {
            browserVersion = "Konqueror";
        }
        return browserVersion;
    }

    /** 
     * ��ȡϵͳ�汾��Ϣ 
     * @param request 
     * @return 
     */
    public static String getRequestSystemInfo(HttpServletRequest request) {
        String systenInfo = null;
        String header = request.getHeader("user-agent");
        if (header == null || header.equals("")) {
            return "";
        }
        //�õ��û��Ĳ���ϵͳ  
        if (header.indexOf("NT 6.0") > 0) {
            systenInfo = "Windows Vista/Server 2008";
        } else if (header.indexOf("NT 5.2") > 0) {
            systenInfo = "Windows Server 2003";
        } else if (header.indexOf("NT 5.1") > 0) {
            systenInfo = "Windows XP";
        } else if (header.indexOf("NT 6.0") > 0) {
            systenInfo = "Windows Vista";
        } else if (header.indexOf("NT 6.1") > 0) {
            systenInfo = "Windows 7";
        } else if (header.indexOf("NT 6.2") > 0) {
            systenInfo = "Windows Slate";
        } else if (header.indexOf("NT 6.3") > 0) {
            systenInfo = "Windows 9";
        } else if (header.indexOf("NT 5") > 0) {
            systenInfo = "Windows 2000";
        } else if (header.indexOf("NT 4") > 0) {
            systenInfo = "Windows NT4";
        } else if (header.indexOf("Me") > 0) {
            systenInfo = "Windows Me";
        } else if (header.indexOf("98") > 0) {
            systenInfo = "Windows 98";
        } else if (header.indexOf("95") > 0) {
            systenInfo = "Windows 95";
        } else if (header.indexOf("Mac") > 0) {
            systenInfo = "Mac";
        } else if (header.indexOf("Unix") > 0) {
            systenInfo = "UNIX";
        } else if (header.indexOf("Linux") > 0) {
            systenInfo = "Linux";
        } else if (header.indexOf("SunOS") > 0) {
            systenInfo = "SunOS";
        }
        return systenInfo;
    }

    /** 
     * ��ȡ�����ߵ��������� 
     * @param ip 
     * @return 
     */
    public static String getHostName(String ip) {
        InetAddress inet;
        try {
            inet = InetAddress.getByName(ip);
            return inet.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 
     * �����ȡmac��ַ 
     * @param cmd 
     * @return 
     */
    private static String callCmd(String[] cmd) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 
     * 
     * 
     * 
     * @param cmd 
     *            ��һ������ 
     * 
     * @param another 
     *            �ڶ������� 
     * 
     * @return �ڶ��������ִ�н�� 
     * 
     */

    private static String callCmd(String[] cmd, String[] another) {
        String result = "";
        String line = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor(); // �Ѿ�ִ�����һ�����׼��ִ�еڶ�������  
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 
     * 
     * 
     * 
     * @param ip 
     *            Ŀ��ip,һ���ھ������� 
     * 
     * @param sourceString 
     *            �����Ľ���ַ��� 
     * 
     * @param macSeparator 
     *            mac�ָ����� 
     * 
     * @return mac��ַ��������ķָ����ű�ʾ 
     * 
     */

    private static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            result = matcher.group(1);
            if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break; // ����ж��IP,ֻƥ�䱾IP��Ӧ��Mac.  
            }
        }
        return result;
    }

    /** 
     * @param ip 
     *            Ŀ��ip 
     * @return Mac Address 
     * 
     */

    private static String getMacInWindows(final String ip) {
        String result = "";
        String[] cmd = { "cmd", "/c", "ping " + ip };
        String[] another = { "cmd", "/c", "arp -a" };
        String cmdResult = callCmd(cmd, another);
        result = filterMacAddress(ip, cmdResult, "-");
        return result;
    }

    /** 
     * 
     * @param ip 
     *            Ŀ��ip 
     * @return Mac Address 
     * 
     */
    private static String getMacInLinux(final String ip) {
        String result = "";
        String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
        String cmdResult = callCmd(cmd);
        result = filterMacAddress(ip, cmdResult, ":");
        return result;
    }

    /** 
     * ��ȡMAC��ַ 
     * 
     * @return ����MAC��ַ 
     */
    public static String getMacAddress(String ip) {
        String macAddress = "";
        macAddress = getMacInWindows(ip).trim();
        if (macAddress == null || "".equals(macAddress)) {
            macAddress = getMacInLinux(ip).trim();
        }
        return macAddress;
    }

    public static List buiding() {
        List<Map> rootList = new ArrayList<Map>();
        Map subMap = new HashMap<String, Object>();
        subMap.put("id", 1);
        subMap.put("open", true);
        subMap.put("name", 1);
        rootList.add(subMap);
        Map subMap1 = new HashMap<String, Object>();
        subMap1.put("id", 2);
        subMap1.put("pId", 1);
        subMap1.put("open", true);
        subMap1.put("name", 2);
        rootList.add(subMap1);
        Map subMap2 = new HashMap<String, Object>();
        subMap2.put("id", 3);
        subMap2.put("pId", 1);
        subMap2.put("open", true);
        subMap2.put("name", 3);
        rootList.add(subMap2);
        Map subMap12 = new HashMap<String, Object>();
        subMap12.put("id", 4);
        subMap12.put("pId", 1);
        subMap12.put("open", true);
        subMap12.put("name", 4);
        rootList.add(subMap12);
        Map subMap121 = new HashMap<String, Object>();
        subMap121.put("id", 5);
        subMap121.put("pId", 4);
        subMap121.put("open", true);
        subMap121.put("name", 5);
        rootList.add(subMap121);
        Map subMap1211 = new HashMap<String, Object>();
        subMap1211.put("id", 6);
        subMap1211.put("pId", 4);
        subMap1211.put("open", true);
        subMap1211.put("name", 6);
        subMap1211.put("dropInner", false);
        rootList.add(subMap1211);
        return rootList;
    }
}
