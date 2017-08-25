package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class SmsServlet extends HttpServlet {

    private static List<Map> list = new ArrayList<Map>();

    private static List<String> oldList = new ArrayList<String>();

    private static Map maptype = new HashMap<String, String>() {
        {
            put("1000000000", "系统消息");
            put("0100000000", "CoCall消息");
            put("0010000000", "Cocall手机短消息");
            put("0001000000", "邮件消息");
            put("0000100000", "通过短信平台发送手机短消息");

        }
    };

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("==============================");
        System.out.println("==============================");
        if ("login".equals(request.getParameter("action"))) {
            String s = request.getParameter("name");
            Map<String, String> m = new HashMap<String, String>();
            request.getSession().setAttribute("name", s);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        if ("del".equals(request.getParameter("action"))) {
            list.clear();
            list = new ArrayList<Map>();
            oldList.clear();
            oldList = new ArrayList<String>();
            request.getSession().removeAttribute("old");
            response.setContentType("text/html;charset=GBK");
            PrintWriter out = response.getWriter();
            out.print("true");
            return;
        }

        if ("getXx".equals(request.getParameter("action"))) {
            List<String> old = (List<String>) request.getSession().getAttribute("old");
            if (null == old || old.size() <= 0) {
                request.getSession().setAttribute("old", new ArrayList<String>());
            }
            StringBuffer sb = new StringBuffer();
            for (Map map : list) {
                if (old.contains(map.get("id"))) {
                    continue;
                } else {
                    old.add((String) map.get("id"));
                    sb.append("<h2><span>" + map.get("head") + "</span></h2>");
                    sb.append("<p><b><a style='color:green'>" + map.get("content") + "</a></b></p>");
                }
            }
            //<h2><span>'+result.head+'</span></h2><p><b>'+result.content+'</b></p>'
            //1.取参数  
            request.getSession().removeAttribute("old");
            request.getSession().setAttribute("old", old);
            response.setContentType("text/html;charset=GBK");
            PrintWriter out = response.getWriter();
            out.print(sb.toString());
            return;
        }
        if ("getXxAll".equals(request.getParameter("action"))) {
            request.getSession().removeAttribute("old");
            List<String> old = new ArrayList<String>();
            StringBuffer sb = new StringBuffer();
            for (Map map : list) {
                old.add((String) map.get("id"));
                sb.append("<h2><span>" + map.get("head") + "</span></h2>");
                sb.append("<p><b><a style='color:green'>" + map.get("content") + "</a></b></p>");
            }
            request.getSession().setAttribute("old", old);
            //<h2><span>'+result.head+'</span></h2><p><b>'+result.content+'</b></p>'
            //1.取参数  
            response.setContentType("text/html;charset=GBK");
            PrintWriter out = response.getWriter();
            out.print(sb.toString());
            return;
        }
        String param = request.getParameter("param");
        String s = new String(param.getBytes("ISO-8859-1"), "UTF-8");
        System.out.println(s);
        Map<String, String> valueMap = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(s), Map.class);
        System.out.println("===" + valueMap.toString());
        StringBuffer sb = new StringBuffer();
        sb.append("第 " + (list.size() + 1) + "条");
        Map map = new HashMap<String, String>();
        UUID uuid = UUID.randomUUID();
        map.put("id", uuid.toString());
        //内容
        map.put("content", valueMap.get("content"));
        //法院
        valueMap.get("targetCorpId");
        sb.append(" 发送单位为：" + valueMap.get("targetCorpId"));
        //发送类型 
        valueMap.get("sendType");
        sb.append(" 发送类型：<a style='color:red'>" + maptype.get(valueMap.get("sendType")) + "</a>");
        //短信id
        valueMap.get("id");
        sb.append(" 短信id：" + valueMap.get("id"));
        //当事人名称
        valueMap.get("attribute10");
        sb.append(" 当事人名称：  <a style='color:red'>" + valueMap.get("attribute10") + "</a>");
        //当事人电话
        valueMap.get("target");
        sb.append(" 当事人电话：<a style='color:red'>" + valueMap.get("target") + "</a>");
        //时间
        valueMap.get("time");
        /*
         * Date d = new Date(Long.parseLong(valueMap.get("time")));
         * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         * java.util.Date date = new java.util.Date(); String str =
         * sdf.format(d); sb.append(" 发送时间：" + str);
         */
        //发送人 id
        valueMap.get("source");
        sb.append(" 发送人 id：" + valueMap.get("source"));
        map.put("head", sb.toString());
        System.out.println(map.toString());
        list.add(map);

        //request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
