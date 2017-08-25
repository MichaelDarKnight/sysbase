/*
 * @(#)IApi.java 2015-8-18 上午09:18:41 fygyzj Copyright 2015 Thuisoft, Inc. All
 * rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com;

/**
 * IApi 抽象出一个IApi来，所有接口均可以继承，包括webservice接口，这样就可以统一出日志了
 * 
 * @author dingjsh
 * @time 2015-8-18上午09:18:41
 */
public interface IApi {

    /**
     * 获得api的key,apikey应用内不得重复
     * 
     * @return 获得api的key
     * @author dingjsh
     * @time 2015-8-3下午03:14:11
     */
    String getKey();

    /**
     * 获得接口名称
     * 
     * @return
     * @author dingjsh
     * @time 2015-8-10下午02:27:05
     */
    String getName();
}
