package com.jiebai.framework.controller.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.jiebai.framework.controller.context.RequestHeaderHolder;
import com.jiebai.framework.controller.context.RequestHeaderInfo;
import com.jiebai.framework.controller.enums.ClientChannelEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * ControllerLogFilter
 *
 * @author lizhihui
 * @version 1.0.0
 */
@Slf4j
@Component
public class HttpTraceLogFilter extends OncePerRequestFilter {

    @NacosValue(value = "${jiebai.controller.trace_log.enable:false}", autoRefreshed = true)
    private boolean logEnable;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        getRequestHeader(request);

        if (!isRequestValid(request) || !logEnable) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            String path = request.getRequestURI();
            HttpTraceLog traceLog = new HttpTraceLog();
            traceLog.setPath(path);
            traceLog.setMethod(request.getMethod());
            long latency = System.currentTimeMillis() - startTime;
            traceLog.setTimeTaken(latency);
            traceLog.setParameterMap(JSON.toJSONString(request.getParameterMap()));
            traceLog.setStatus(status);
            traceLog.setRequestBody(getRequestBody(request));
            traceLog.setResponseBody(getResponseBody(response));
            log.info("Http trace log: {}", JSON.toJSONString(traceLog));
            updateResponse(response);
            RequestHeaderHolder.removeHeaderInfo();
        }
    }

    /**
     * 获取请求Header信息，并放入threadlocal
     *
     * @param request HttpServletRequest
     * @version 1.0.1
     */
    private void getRequestHeader(HttpServletRequest request) {
        String clientVersion = request.getHeader("Client-Version");
        String clientChannel = request.getHeader("Client-Channel");
        String shopIdString = request.getHeader("Shop-Id");
        String userAgent = request.getHeader("User-Agent");
        String clientIp = request.getHeader("X-Real-IP");

        Integer shopId = null;
        try {
            shopId = Integer.valueOf(shopIdString);
        } catch (NumberFormatException nfe) {
            log.warn("getRequestHeader() {Shop-Id:" + shopIdString + "} must be a number !");
        }

        RequestHeaderInfo requestHeaderInfo = new RequestHeaderInfo();
        requestHeaderInfo.setClientVersion(clientVersion);
        requestHeaderInfo.setClientChannel(ClientChannelEnum.get(clientChannel));
        requestHeaderInfo.setShopId(shopId);
        requestHeaderInfo.setUserAgent(userAgent);
        requestHeaderInfo.setClientIp(clientIp);
        RequestHeaderHolder.setHeaderInfo(requestHeaderInfo);
    }

    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding())
                    .replaceAll("[\\r\\n\\s]", "");
            } catch (IOException e) {
            }
        }
        return requestBody;
    }

    private String getResponseBody(HttpServletResponse response) {
        String responseBody = "";
        ContentCachingResponseWrapper wrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                responseBody = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding())
                    .replaceAll("[\\r\\n\\s]", "");
            } catch (IOException e) {
            }
        }
        return responseBody;
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }

    @Data
    private static class HttpTraceLog {
        private String path;
        private String parameterMap;
        private String method;
        private Long timeTaken;
        private Integer status;
        private String requestBody;
        private String responseBody;
    }
}
