package com.jiebai.framework.controller.context;

import com.jiebai.framework.controller.enums.ClientChannelEnum;
import lombok.Data;

/**
 * 请求header信息
 *
 * @author lizhihui
 * @version 1.0.1
 */
@Data
public class RequestHeaderInfo {

    /**
     * 客户端版本号
     */
    private String clientVersion;

    /**
     * 客户端渠道
     */
    private ClientChannelEnum clientChannel;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 客户端设备信息
     */
    private String userAgent;

    /**
     * 客户端ip
     */
    private String clientIp;
}
