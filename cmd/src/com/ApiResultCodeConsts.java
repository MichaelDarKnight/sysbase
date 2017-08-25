/*
 * @(#)ApiResultCodeConsts.java 2015-8-3 下午04:21:05 fygyzj Copyright 2015
 * Thuisoft, Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com;

/**
 * ApiResultCodeConsts
 * 
 * @author zhaoshuai by dingjsh
 * @time 2015-8-3下午04:21:05
 */
public interface ApiResultCodeConsts {

    /** 成功 */
    int SUCCESS = 1;

    /** 未分类错误 */
    int COMMON_ERROR = 2;

    /** 服务器异常 */
    int SERVER_ERROR = 100;

    // ================= 200到300之间代表接口调用的通用错误

    /** 未授权 */
    int UN_AUTHORIZED = 200;

    /** 找不到对应的接口 */
    int API_NOT_FOUND = 201;

    /** 未注册服务 */
    int SYSTEM_ID_NOT_REGISTERED = 202;

    /** 参数不合法 */
    int PARAM_NOT_VALID = 203;

    /** 请求被拒绝 */
    int SERVICE_DENIED = 204;

    /** authcode非法 */
    int AUTHCODE_NOT_VALID = 205;

    /** 数据状态变了 */
    int DATA_CHANGED = 400;

    /** 数据找不到 */
    int DATA_NOT_FOUND = 401;
}
