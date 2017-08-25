/*
 * @(#)SysprocessesServlet.java 2017-8-24 上午9:15:40
 * dbkill
 * Copyright 2017 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.thunisoft.sysbase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SysprocessesServlet
 * @author Administrator
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class SysprocessesServlet extends HttpServlet {
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doPost(req, resp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("getXx".equals(req.getParameter("action"))) {
            writer(resp);
            return;
        }
        if ("kill".equals(req.getParameter("action"))) {
            if (req.getParameter("ip") != null && !req.getParameter("ip").isEmpty()) {
                kill(req.getParameter("ip"));
            }
            writer(resp);
            return;
        }
    }

    /**
     * 
     */
    private void kill(String ip) {
        List list1 = Conn.query("SELECT spid  from master.dbo.sysprocesses WHERE ipaddr = '" + ip + "' ");
        for (Object o : list1) {
            Map m = (Map) o;
            Conn.exec(" kill " + m.get("spid"));
        }
    }

    /**
     * @param resp
     * @throws IOException
     */
    private void writer(HttpServletResponse resp) throws IOException {
        StringBuffer sb = new StringBuffer();
        List list = Conn
                .query("SELECT ipaddr,hostname,count(*) a  from master.dbo.sysprocesses GROUP BY ipaddr,hostname ORDER BY a desc");
        sb.append("<table width=\"100%\">");
        sb.append("<tr>");
        sb.append("<th>").append("ipaddr");
        sb.append("</th>");
        sb.append("<th>").append("hostname");
        sb.append("</th>");
        sb.append("<th>").append("连接数");
        sb.append("</th>");
        sb.append("<th>").append("操作");
        sb.append("</th>");
        sb.append("</tr>");
        for (Object obj : list) {
            Map map = (Map) obj;
            sb.append("<tr>");
            sb.append("<td>").append(map.get("ipaddr"));
            sb.append("</td>");
            sb.append("<td>").append(map.get("hostname"));
            sb.append("</td>");
            sb.append("<td>").append(map.get("a"));
            sb.append("</td>");
            sb.append("<td>").append("<input type=\"button\" onclick=\"killAll('").append(map.get("ipaddr"))
                    .append("')\" value=\"干掉\">");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(sb.toString());
    }
}
