package com.jiebai.framework.controller.base;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * AbstractBaseController
 *
 * @author lizhihui
 * @version 1.0.0
 */
public abstract class AbstractBaseController {

    /**
     * 获取当前线程的登录用户user对象
     *
     * @return LoginUserEntity
     */
    public static final LoginUserEntity getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof LoginUserEntity) {
                return (LoginUserEntity)principal;
            }
        }
        return null;
    }

}
