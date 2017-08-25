/*
 * @(#)IApi.java 2015-8-3 下午03:13:40 fygyzj Copyright 2015 Thuisoft, Inc. All
 * rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com;

import java.util.Map;

/**
 * IApi 所有的http接口继承此接口，便于统一处理 TODO dingjsh commented in 20150810 出一个接口统一规范
 * 
 * @author dingjsh
 * @time 2015-8-3下午03:13:40
 */
public interface IHttpApi extends IApi {

    /** 是否支持压缩 如果支持压缩的话，数据以gzip压缩传递 */
    String HEADER_SUPPORT_COMPRESS = "supportCompress";

    String HEADER_SYSTEM_ID = "systemId";

    String HEADER_AUTH_CODE = "authcode";

    /**
     * 执行接口调用方法,之所有不传response对象给接口是希望能统一处理resonse
     * 
     * @param request
     *            request对象
     * @return 返回请求处理结果，结果将会写到response中
     * @author dingjsh
     * @time 2015-8-3下午03:17:50
     */
    @SuppressWarnings("rawtypes")
    ApiResult execute(String action, Map<String, String> headers, Map<String, Object> params);

}
