/*
 * @(#)HttpUtils.java 2015-8-3 下午05:15:31 fygyzj Copyright 2015 Thuisoft, Inc.
 * All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.ws.rs.HttpMethod;

import net.sf.json.JSON;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.util.GetMethod;

import sun.net.www.http.HttpClient;

/**
 * HttpUtils
 * 
 * @author dingjsh
 * @time 2015-8-3下午05:15:31
 */
public class HttpUtils {

    private static final Log LOG = LogFactory.getLog(HttpUtils.class);

    private static final int DEFAULT_SO_TIMEOUT = 8000;

    private static final int DEFAULT_CONNECTION_TIMEOUT = 8000;

    //    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String gzipAndEncodeBase64(String value) {
        try {
            byte[] valueBytes = value.getBytes("UTF-8");
            return gzipAndEncodeBase64(valueBytes);
        } catch (Exception e) {
            LOG.error("gzip压缩失败", e);
            return StringUtils.EMPTY;
        }
    }

    public static final String gzipAndEncodeBase64(byte[] value) {
        try {
            GZIPOutputStream gzip = null;
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(obj);
            gzip.write(value, 0, value.length);
            gzip.close();
            return Base64.encodeBase64String(obj.toByteArray());
        } catch (Exception e) {
            LOG.error("gzip压缩失败", e);
            return StringUtils.EMPTY;
        }
    }

    public static String decodeBase64AndUnGzip(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        GZIPInputStream gis = null;
        try {
            // int originalLength = value.getBytes("UTF-8").length;
            long start = System.currentTimeMillis();
            gis = new GZIPInputStream(new ByteArrayInputStream(Base64.decodeBase64(value)));
            byte[] bytes = IOUtils.toByteArray(gis);
            value = new String(bytes, "UTF-8");
            LOG.debug("gzip接压缩耗时【" + (System.currentTimeMillis() - start) + "】ms");
        } catch (Exception e) {
            LOG.error("解压缩gzip失败", e);
            value = "";
        } finally {
            IOUtils.closeQuietly(gis);
        }
        return value;
    }

    public static final ApiResult<String> gzipAndEncodeBase64(ApiResult<?> apiResult) {
        Object value = apiResult.getData();
        ApiResult<String> newResult = null;
        if (null != value) {
            if (value instanceof String) {
                // 如果本身是string还做tostring处理会出现双引号
                String gzipValue = gzipAndEncodeBase64((String) value);
                newResult = new ApiResult<String>(gzipValue, true);
            } else if (value instanceof byte[]) {
                String gzipValue = gzipAndEncodeBase64((byte[]) value);
                newResult = new ApiResult<String>(gzipValue, true);
            } else {
                String jsonString = JSON.toJSONString(value);
                String gzipValue = gzipAndEncodeBase64(jsonString);
                newResult = new ApiResult<String>(gzipValue, true);
            }
        } else {
            newResult = new ApiResult<String>(StringUtils.EMPTY);
            newResult.setCode(apiResult.getCode());
            newResult.setMessage(apiResult.getMessage());
        }
        return newResult;
    }

    public static String get(String uri) {
        return get(uri, null, null);
    }

    public static String get(String uri, Integer soTimeout, Integer connectionTimeout) {
        return sendHttpRequest("get", uri, soTimeout, connectionTimeout);
    }

    public static String post(String uri) {
        return post(uri, null, null);
    }

    public static String post(String uri, Integer soTimeout, Integer connectionTimeout) {
        return sendHttpRequest("post", uri, soTimeout, connectionTimeout);
    }

    private static String sendHttpRequest(String httpMethod, String uri, Integer soTimeout, Integer connectionTimeout) {
        long start = System.currentTimeMillis();
        String result = StringUtils.EMPTY;
        try {
            HttpMethod method = null;
            //
            if ("get".equals(httpMethod)) {
                method = new GetMethod(uri);
            } else {
                method = new PostMethod(uri);
            }
            HttpClient client = getHttpClient(soTimeout, connectionTimeout);

            client.executeMethod(method);
            result = method.getResponseBodyAsString();
            long duraction = System.currentTimeMillis() - start;
            LOG.debug("调用http接口【" + uri + "】结束，耗时【" + duraction + "ms】");
            LOG.debug("返回值【" + result + "】");
        } catch (HttpException e) {
            LOG.error("调用http接口【" + uri + "】出错:网络访问异常", e);
        } catch (IOException e) {
            LOG.error("调用http接口【" + uri + "】出错", e);
        }
        return result;

    }

    private static HttpClient getHttpClient(Integer soTimeout, Integer connectionTimeout) {
        HttpClient client = new HttpClient();
        if (null == soTimeout) {
            soTimeout = DEFAULT_SO_TIMEOUT;
        }
        if (null == connectionTimeout) {
            connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
        }
        client.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
        return client;
    }

    /**
     * 获得header值，如果是http.useParam.asHead=true的话，就从param中取，
     * 这么做的好处是调试时可以直接通过浏览器就可以测试了 不要乱用，主要是为了开发人员调试方便
     * 
     * @param headers
     *            请求头
     * @param params
     *            请求参数
     * @param headerKey
     *            头信息key
     * @return 值
     * @author dingjsh
     * @time 2015-8-21下午02:10:38
     */
    public static String getHeaderOrGetParamInDebugMode(Map<String, String> headers, Map<String, Object> params,
            String headerKey) {
        String value = headers.get(headerKey);
        if (null == value) {
            boolean isDebug = false;
            try {
                // dingjsh commented in 20150821
                // artery这个地方有可能会报空指针，通常是由于bean初始化顺序的原因
                // 之所以不用debug属性是怕开发人员没注意到，结果联调时没问题，发到现场就有问题了
                // 这个属性就是个后门，不要乱用，不要提交
                isDebug = ArteryConfigUtil.getBoolProperty("http.useParam.asHead");
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            if (isDebug) {
                Object valueInParam = params.get(headerKey);
                if (null != valueInParam) {
                    value = ObjectUtils.toString(valueInParam);
                }
            }
        }
        return value;
    }

    /**
     * 避免出现参数乱码
     * 
     * @param valueArr
     *            参数
     * @return 转换编码后的参数值
     * @author dingjsh
     * @time 2015-8-28下午01:44:39
     */
    public static String[] preventGarble(String[] valueArr) {
        if (null == valueArr || valueArr.length == 0) {
            return valueArr;
        }
        int length = valueArr.length;
        String[] newValueArr = new String[length];
        for (int index = 0; index < length; index++) {
            String value = valueArr[index];
            newValueArr[index] = preventGarble(value);
        }
        return newValueArr;
    }

    /**
     * 避免出现参数乱码
     * 
     * @param value
     *            参数值
     * @return 转换编码后的参数值
     * @author dingjsh
     * @time 2015-8-28下午01:49:05
     */
    public static String preventGarble(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        try {
            String convertedValue = new String(value.getBytes("ISO-8859-1"), "utf-8");
            // UTF-8是多字节的，如果出现下面这种情况，说明要么传过来的没有中文，要么中文被吃掉了，所以要还原
            if (convertedValue.length() == convertedValue.getBytes("UTF-8").length) {
                convertedValue = value;
            }
            return convertedValue;
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage(), e);
            return value;
        }
    }

    public static JsonConfig dealDiffDateTypeWithJsonConf() {
        JsonConfig config = new JsonConfig();
        //由于从数据库中直接拿取数据，时间的类型可能有多种，所以需要对多个关于时间的json进行处理
        config.registerJsonValueProcessor(java.sql.Date.class, DateJsonValueProcessor.getInstance(DATE_TIME_FORMAT));
        config.registerJsonValueProcessor(Date.class, DateJsonValueProcessor.getInstance(DATE_TIME_FORMAT));
        config.registerJsonValueProcessor(java.sql.Timestamp.class,
            DateJsonValueProcessor.getInstance(DATE_TIME_FORMAT));
        return config;
    }

    public static void main(String[] args) throws HttpException, IOException {

    }
}
