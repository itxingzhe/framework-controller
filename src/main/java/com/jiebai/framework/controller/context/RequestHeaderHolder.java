package com.jiebai.framework.controller.context;

/**
 * 请求header信息持有者
 *
 * @author lizhihui
 * @version 1.0.1
 */
public class RequestHeaderHolder {

    private static ThreadLocal<RequestHeaderInfo> HttpRequestThreadLocalHolder = new ThreadLocal<>();

    /**
     * 设置header信息
     *
     * @param info RequestHeaderInfo
     */
    public static void setHeaderInfo(RequestHeaderInfo info) {
        HttpRequestThreadLocalHolder.set(info);
    }

    /**
     * 获取header信息
     *
     * @return RequestHeaderInfo
     */
    public static RequestHeaderInfo getHeaderInfo() {
        return HttpRequestThreadLocalHolder.get();
    }

    /**
     * 清除
     */
    public static void removeHeaderInfo() {
        HttpRequestThreadLocalHolder.remove();
    }
}
