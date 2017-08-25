package com;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.xml.internal.bind.api.TypeReference;

/**
 * ApiResultUtils 用于解析调用UIM接口的返回值
 * 
 * @author dingjsh
 * @time 2015-2-9上午09:05:59
 */
public class ApiResultUtils {

    private static final Log LOG = LogFactory.getLog(ApiResultUtils.class);

    /**
     * 通过apiResult的字符串解析为ApiResult<T> 对象
     * 
     * @param <T>
     *            泛型
     * @param apiResultStr
     *            apiResult的字符串
     * @param clazz
     *            泛型对应的类
     * @return ApiResult<T> 对象
     * @author dingjsh
     * @time 2015-2-9上午09:44:29
     */
    public static final <T> ApiResult<T> getApiResult(String apiResultStr, Class<T> clazz) {
        if (StringUtils.isNotBlank(apiResultStr) && null != clazz) {
            ApiResult<T> apiResult = JSONObject.parseObject(apiResultStr, new TypeReference<ApiResult<T>>() {
            });
            if (apiResult.getData() instanceof JSONObject) {
                apiResult.setData(JSON.toJavaObject((JSONObject) apiResult.getData(), clazz));
            }
            return apiResult;
        } else {
            LOG.error("参数不正确");
            return null;
        }
    }

    /**
     * 是否成功
     * 
     * @param <T>
     *            泛型
     * @param apiResult
     *            apiResult
     * @return 是否成功
     * @author dingjsh
     * @time 2015-2-9上午09:47:08
     */
    public static final <T> boolean isSuccess(ApiResult<T> apiResult) {
        if (null == apiResult) {
            LOG.error("apiResult为null");
            return false;
        } else {
            return ApiResultCodeConsts.SUCCESS == apiResult.getCode();
        }
    }

    /**
     * 是否成功
     * 
     * @param apiResultStr
     *            json字符串
     * @return 是否成功
     * @author dingjsh
     * @time 2015-2-9上午10:00:28
     */
    public static final boolean isSuccess(String apiResultStr) {
        boolean isSuccess = false;
        if (StringUtils.isNotBlank(apiResultStr)) {
            JSONObject json = JSONObject.parseObject(apiResultStr);
            isSuccess = isSuccess(json);
        }
        return isSuccess;
    }

    /**
     * 是否成功
     * 
     * @param json
     *            json对象
     * @return 是否成功
     * @author dingjsh
     * @time 2015-3-2上午10:27:47
     */
    public static final boolean isSuccess(JSONObject json) {
        boolean isSuccess = false;
        if (null != json) {
            isSuccess = ApiResultCodeConsts.SUCCESS == json.getIntValue("code");
        }
        return isSuccess;
    }

    /**
     * 获得apiResult中的数据
     * 
     * @param <T>
     *            泛型
     * @param apiResult
     *            apiResult
     * @return apiResult中的数据
     * @author dingjsh
     * @time 2015-2-9上午09:47:35
     */
    public static final <T> T getData(ApiResult<T> apiResult) {
        if (null == apiResult) {
            LOG.error("apiResult为null");
            return null;
        } else {
            return apiResult.getData();
        }
    }

    /**
     * 获得apiResult中的消息
     * 
     * @param <T>
     *            泛型
     * @param apiResult
     *            apiResult
     * @return 消息
     * @author dingjsh
     * @time 2015-2-9上午09:49:34
     */
    public static final <T> String getMessage(ApiResult<T> apiResult) {
        if (null == apiResult) {
            LOG.error("apiResult为null");
            return StringUtils.EMPTY;
        } else {
            return apiResult.getMessage();
        }
    }

}
