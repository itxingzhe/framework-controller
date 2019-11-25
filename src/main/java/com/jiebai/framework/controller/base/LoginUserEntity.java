package com.jiebai.framework.controller.base;

import lombok.Data;

/**
 * 登录用户信息
 *
 * @author lizhihui
 * @version 1.0.0
 */
@Data
public class LoginUserEntity {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private Integer mobile;

    /**
     * 头像路径
     */
    private String headimgurl;

    /**
     * 游客-GUEST(非会员); 金卡-FANS; 黑卡-NORMAL; 旗舰店-ULTIMATE; 大客户经理-MANAGER; 合伙人-PARTNER; 购物满199体验会员-EXPERIENCE;  (可成为会员)-QUALIFIED; 原688会员-688NORMAL; 高佣体验会员-EXPERIENCE_GY;
     */
    private String userMemberRole;

    /**
     * 直推上一级id(必须是会员,游客是不能推荐的)
     */
    private Integer parentMemberId;

    /**
     * 最后访问商城Id
     */
    private Integer lastvisitMallid;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 渠道短码
     */
    private String channelCode;
    /**
     * 登陆手机号
     */
    private String loginMobile;

}
