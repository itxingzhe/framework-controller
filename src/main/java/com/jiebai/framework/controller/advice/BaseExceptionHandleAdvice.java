package com.jiebai.framework.controller.advice;

import com.jiebai.framework.controller.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

/**
 * Controller统一异常处理
 *
 * @author lizhihui
 * @version 1.0.0
 */
@Slf4j
public class BaseExceptionHandleAdvice {

    /**
     * 请求参数requestBody转换异常处理
     * 大多情况为requestBody为null
     *
     * @param notReadableException HttpMessageNotReadableException
     * @return ResultVO
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultVO> validExceptionHandler(HttpMessageNotReadableException notReadableException) {
        String message = notReadableException.getMessage();
        if (message.startsWith("Required request body is missing")) {
            return ResponseEntity.badRequest().body(ResultVO.badRequest("request body 不能为空"));
        }
        if (message.startsWith("JSON parse error")) {
            return ResponseEntity.badRequest().body(ResultVO.badRequest("request body JSON解析失败"));
        }
        return ResponseEntity.badRequest().body(ResultVO.badRequest(message));
    }

    /**
     * 请求参数requestBody验证错误异常处理
     *
     * @param validException MethodArgumentNotValidException
     * @return ResultVO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultVO> validExceptionHandler(MethodArgumentNotValidException validException) {
        FieldError fieldError = validException.getBindingResult().getFieldError();
        String message = "";
        if (Objects.nonNull(fieldError)) {
            message = String.format("[%s]%s", fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(ResultVO.badRequest(message));
    }

    /**
     * 请求参数queryObject验证错误异常处理
     *
     * @param bindException BindException
     * @return ResultVO
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResultVO> validExceptionHandler(BindException bindException) {
        FieldError fieldError = bindException.getBindingResult().getFieldError();
        String message = "";
        if (Objects.nonNull(fieldError)) {
            message = String.format("[%s]%s", fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(ResultVO.badRequest(message));
    }

    /**
     * 请求单参数验证错误异常处理
     *
     * @param constraintViolationException constraintViolationException
     * @return ResultVO
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResultVO> validExceptionHandler(ConstraintViolationException constraintViolationException) {
        String message = constraintViolationException.getMessage();
        // dubbo service的异常
        if (StringUtils.isNotBlank(message) && message.startsWith("Failed to validate service")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResultVO.fail("调用service服务异常：接口参数无效", message));
        }
        // api的异常
        return ResponseEntity.badRequest().body(ResultVO.badRequest(message));
    }

    /**
     * dubbo调用异常处理
     *
     * @param rpcException RpcException
     * @return ResultVO
     */
    @ExceptionHandler(RpcException.class)
    public ResponseEntity<ResultVO> rpcExceptionHandler(RpcException rpcException) {
        log.error("dubbo调用异常 {}", rpcException.getMessage(), rpcException);
        String exceptionMessage = parseRpcExceptionMessage(rpcException);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultVO.fail("业务处理异常", exceptionMessage));
    }

    /**
     * 未知异常处理
     *
     * @param e Exception
     * @return ResultVO
     */
    @ExceptionHandler
    public ResponseEntity<ResultVO> handler(Exception e) {
        log.error("未知异常 {}", e.getMessage(), e);
        String message = e.getClass().getName() + ":" + e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultVO.fail("服务器响应异常", message));
    }

    /**
     * 解析RPC异常信息
     *
     * @param rpcException RpcException
     * @return String
     */
    private String parseRpcExceptionMessage(RpcException rpcException) {
        if (rpcException.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException ve = (ConstraintViolationException)rpcException.getCause();
            // 可以拿到一个验证错误详细信息的集合
            Set<ConstraintViolation<?>> violations = ve.getConstraintViolations();
            return violations.toString();
        }
        return rpcException.getMessage();
    }

}
