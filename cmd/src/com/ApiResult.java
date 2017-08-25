/*
 * @(#)ApiResult.java 2015-8-3 下午04:18:40 fygyzj Copyright 2015 Thuisoft, Inc.
 * All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com;

import net.sf.json.JSON;

/**
 * ApiResult
 * 
 * @author dingjsh
 * @time 2015-8-3下午04:18:40
 */
public class ApiResult<T> {

    /**
     * 1代码成功
     */
    private int code;

    /**
     * 返回消息 如果code不是1时，这代表出错信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 是否压缩数据，如果压缩的话，采用的是gzip+base64 暂不考虑扩展其它方案，不出意外，应该不会换
     */
    private boolean compressData;

    @SuppressWarnings("rawtypes")
    private static final ApiResult SUCCESS_RESULT = new ApiResult(ApiResultCodeConsts.SUCCESS, "");

    public ApiResult() {
        super();
        this.code = ApiResultCodeConsts.SUCCESS;
    }

    public ApiResult(T data) {
        super();
        this.code = ApiResultCodeConsts.SUCCESS;
        this.data = data;
    }

    public ApiResult(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ApiResult(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(int code, String message, T data, boolean compressData) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
        this.compressData = compressData;
    }

    public ApiResult(T data, boolean compressData) {
        this(data);
        this.compressData = compressData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isCompressData() {
        return compressData;
    }

    public void setCompressData(boolean compressData) {
        this.compressData = compressData;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @SuppressWarnings("unchecked")
    public static final <T> ApiResult<T> getSuccessInstance() {
        return (ApiResult<T>) SUCCESS_RESULT;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(ApiResult.getSuccessInstance()));
    }
}
