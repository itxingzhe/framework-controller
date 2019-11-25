package com.jiebai.framework.controller.advice;

import com.jiebai.framework.controller.vo.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * controller response body 统一处理
 *
 * @author lizhihui
 * @version 1.0.0
 */
@RestControllerAdvice(annotations = RestController.class)
public class ControllerResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ResultVO) {
            ResultVO resultVO = (ResultVO)body;
            HttpStatus httpStatus = resultVO.getHttpStatus();
            response.setStatusCode(httpStatus);
        }

        return body;

    }

}