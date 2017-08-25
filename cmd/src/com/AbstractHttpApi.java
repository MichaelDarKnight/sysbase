/*
 * @(#)AbstractHttpApi.java 2015-8-5 下午01:51:37 fygyzj Copyright 2015 Thuisoft,
 * Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpUtils;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AbstractHttpApi
 * 
 * @author dingjsh
 * @time 2015-8-5下午01:51:37
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractHttpApi implements IHttpApi {

    protected Log logger = LogFactory.getLog(this.getClass());

    private static final String[] DATE_PATTERNS = new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss SSS" };

    @Override
    public ApiResult execute(String action, Map<String, String> headers, Map<String, Object> params) {
        if (action.indexOf('.') > 0) {
            action = action.substring(action.indexOf('.') + 1);
        }
        try {
            Method method = this.getClass().getDeclaredMethod(action, Map.class, Map.class);
            method.setAccessible(true);
            return (ApiResult) method.invoke(this, headers, params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ApiResult(ApiResultCodeConsts.API_NOT_FOUND, "接口调用不成功，action【" + action + "】");
    }

    protected final boolean isSupportCompress(Map<String, String> headers, Map<String, Object> params) {
        String supportCompress = HttpUtils.getHeaderOrGetParamInDebugMode(headers, params,
            IHttpApi.HEADER_SUPPORT_COMPRESS);
        return "true".equalsIgnoreCase(supportCompress);
    }

    protected final Object getParamter(Map<String, Object> params, String key) {
        Object value = null;
        if (null != params && StringUtils.isNotBlank(key)) {
            value = params.get(key);
        }
        return value;
    }

    protected final boolean getParameterBoolean(Map<String, Object> params, String key) {
        Object valueObj = getParamter(params, key);
        return "true".equals(String.valueOf(valueObj));
    }

    protected final String getParameterString(Map<String, Object> params, String key) {
        Object valueObj = getParamter(params, key);
        return ObjectUtils.toString(valueObj);
    }

    protected final JSONObject getParameterJSON(Map<String, Object> params, String key) {
        String value = getParameterString(params, key);
        logger.error("-----------------------------------");
        logger.error(key);
        logger.error(value);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JSON.parseObject(value);
    }

    protected final Date getParameterDate(Map<String, Object> params, String key) {
        String value = getParameterString(params, key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (NumberUtils.isNumber(value)) {
            return new Date(Long.parseLong(value));
        }
        try {
            return DateUtils.parseDate(value, DATE_PATTERNS);
        } catch (ParseException e) {
            logger.error("无法将【" + value + "】转成Date类型," + "只支持" + Arrays.toString(DATE_PATTERNS), e);
            return null;
        }
    }

    protected final Integer getParameterInteger(Map<String, Object> params, String key) {
        Object value = getParamter(params, key);
        if (null != value && NumberUtils.isNumber(value.toString())) {
            return Integer.valueOf(value.toString());
        }
        return null;
    }

    protected final int[] getParameterIntArray(Map<String, Object> params, String key) {
        int[] result = null;
        String value = getParameterString(params, key);
        if (null != value) {
            JSONArray array = JSONArray.parseArray(value);
            result = new int[array.size()];
            for (int i = 0; i < array.size(); i++) {
                result[i] = array.getIntValue(i);
            }
        }
        return result;
    }

    protected final Object[] getParameterObjArray(Map<String, Object> params, String key) {
        Object[] result = null;
        String value = getParameterString(params, key);
        if (null != value) {
            JSONArray array = JSONArray.parseArray(value);
            result = new Object[array.size()];
            for (int i = 0; i < array.size(); i++) {
                result[i] = array.get(i);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    protected List getParamterList(Map<String, Object> params, String key, Class clazz) {
        List result = null;
        String value = getParameterString(params, key);
        if (null != value) {
            JSONArray array = JSONArray.parseArray(value);
            result = new ArrayList();
            for (int i = 0; i < array.size(); i++) {
                result.add(array.getObject(i, clazz));
            }
        }
        return result;
    }

    protected final <T> T getParamter(Map<String, Object> params, String key, Class<T> clazz) {
        JSONObject value = getParameterJSON(params, key);
        if (null == value) {
            return null;
        }
        return JSON.toJavaObject(value, clazz);
    }
}
