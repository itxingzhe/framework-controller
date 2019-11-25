package com.jiebai.framework.controller.enums;

/**
 * 客户端渠道枚举
 *
 * @author lizhihui
 * @version 1.0.1
 */
public enum ClientChannelEnum {

    /**
     * ios全球时刻
     */
    IOS_QQSK("IOS_QQSK"),
    /**
     * 安卓全球时刻
     */
    ANDROID_QQSK("ANDROID_QQSK"),
    /**
     * H5微商城
     */
    H5_QQSK("H5_QQSK"),
    /**
     * 微信小程序_苛选商城
     */
    WECHART_KXSC("WECHART_KXSC"),
    /**
     * 微信小程序_精选商城
     */
    WECHART_JXSC("WECHART_JXSC"),
    /**
     * 微信小程序_买手商城
     */
    WECHART_MSSC("WECHART_MSSC"),
    /**
     * 微信小程序_时刻团
     */
    WECHART_SKT("WECHART_SKT"),
    /**
     * 微信小程序_为邻精选
     */
    WECHART_WLJX("WECHART_WLJX"),
    /**
     * 微信小程序_中通快运
     */
    WECHART_ZTKY("WECHART_ZTKY"),
    /**
     * 微信小程序_中通创客
     */
    WECHART_ZTCK("WECHART_ZTCK"),
    /**
     * 未知渠道
     */
    UNKNOWN("UNKNOWN");

    private String name;

    ClientChannelEnum(String name) {
        this.name = name;
    }

    /**
     * 是否为微信小程序
     *
     * @return boolean true-是 false-否
     */
    public boolean isWechat() {
        if (this.name.startsWith("WECHART_")) {
            return true;
        }
        return false;
    }

    public static ClientChannelEnum get(String name) {
        for (ClientChannelEnum clientChannelEnum : ClientChannelEnum.values()) {
            if (clientChannelEnum.getName().equals(name)) {
                return clientChannelEnum;
            }
        }
        return ClientChannelEnum.UNKNOWN;
    }

    public String getName() {
        return name;
    }
}
