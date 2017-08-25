/*
 * @(#)Main.java 2017-8-23 下午5:23:03
 * sysbase
 * Copyright 2017 Thuisoft, Inc. All rights reserved.
 * THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.thunisoft.sysbase;

import java.util.List;
import java.util.Map;

/**
 * Main
 * @author Administrator
 * @version 1.0
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        List list = Conn
                .query("SELECT ipaddr,hostname,count(*) a  from master.dbo.sysprocesses GROUP BY ipaddr,hostname ORDER BY a desc");
        System.out.println(list.toString());
        List list1 = Conn.query("SELECT spid  from master.dbo.sysprocesses WHERE ipaddr = '172.18.45.95' ");
        System.out.println(list1.toString());
        for (Object o : list1) {
            Map m = (Map) o;
            Conn.exec(" kill " + m.get("spid"));
        }
    }

}
