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
         * System.out.println("===========接口调用成功==========="); // 1.01
         * getParameter(String strTextName) 获取表单提交的信息。 String strName =
         * request.getParameter("name"); System.out.println(strName + ">>>>>1");
         * // 1.02 getProtocol() 获取客户使用的协议。 String strProtocol =
         * request.getProtocol(); System.out.println(strProtocol + ">>>>>2"); //
         * 1.03 getServletPath() 获取客户提交信息的页面。 String strServlet =
         * request.getServletPath(); System.out.println(strServlet + ">>>>>3");
         * // 1.04 getMethod() 获取客户提交信息的方式，get|post。 String strMethod =
         * request.getMethod(); System.out.println(strMethod + ">>>>>4"); //
         * 1.05 getHeade() 获取HTTP头文件中的accept、accept-encoding和Host的值。 String
         * strHeader = request.getHeader("accept"); System.out.println(strHeader
         * + ">>>>>5"); // 1.06 getRermoteAddr() 获取客户的IP地址。 String strIP =
         * request.getRemoteAddr(); System.out.println(strIP + ">>>>>6"); //
         * 1.07 getRemoteHost() 获取客户机的名称。 String clientName =
         * request.getRemoteHost(); System.out.println(clientName + ">>>>>7");
         * // 1.08 getServerName() 获取服务器名称。 String serverName =
         * request.getServerName(); System.out.println(serverName + ">>>>>8");
         * // 1.09 getServerPort() 获取服务器的端口号。 int serverPort =
         * request.getServerPort(); System.out.println(serverPort +
         * "getIpAddr"); // 1.10 getParameterNames() 获取客户端提交的所有参数的名字。
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
     * 获取访问者IP 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。 
     *  
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 
     * 如果还不存在则调用Request .getRemoteAddr()。 
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
            // 多次反向代理后会有多个IP值，第一个为真实IP。  
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
     * 获取来访者的浏览器版本 
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
     * 获取系统版本信息 
     * @param request 
     * @return 
     */
    public static String getRequestSystemInfo(HttpServletRequest request) {
        String systenInfo = null;
        String header = request.getHeader("user-agent");
        if (header == null || header.equals("")) {
            return "";
        }
        //得到用户的操作系统  
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
     * 获取来访者的主机名称 
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
     * 命令获取mac地址 
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
     *            第一个命令 
     * 
     * @param another 
     *            第二个命令 
     * 
     * @return 第二个命令的执行结果 
     * 
     */

    private static String callCmd(String[] cmd, String[] another) {
        String result = "";
        String line = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令  
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
     *            目标ip,一般在局域网内 
     * 
     * @param sourceString 
     *            命令处理的结果字符串 
     * 
     * @param macSeparator 
     *            mac分隔符号 
     * 
     * @return mac地址，用上面的分隔符号表示 
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
                break; // 如果有多个IP,只匹配本IP对应的Mac.  
            }
        }
        return result;
    }

    /** 
     * @param ip 
     *            目标ip 
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
     *            目标ip 
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
     * 获取MAC地址 
     * 
     * @return 返回MAC地址 
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
