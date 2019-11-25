package com.jiebai.framework.controller.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 响应结果对象
 *
 * @author lizhihui
 * @version 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResultVO<T> extends BaseResultVO {

    private T data;

    public static <T> ResultVO<T> success() {
        return new ResultVO<T>();
    }

    /**
     * 构建业务处理成功Result
     * HttpStatus=200
     *
     * @param data 响应内容
     * @param <T> 响应对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<T>(data);
    }

    /**
     * 构建请求参数错误Result
     * HttpStatus=400
     *
     * @param message 错误原因
     * @param <T> 响应对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> badRequest(String message) {
        return new ResultVO(message, null, HttpStatus.BAD_REQUEST);
    }

    /**
     * 构建服务端处理失败或异常Result
     * HttpStatus=500
     *
     * @param message 失败原因
     * @param <T> 响应对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> fail(String message) {
        return new ResultVO(message, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 构建服务端处理失败或异常Result
     * HttpStatus=500
     *
     * @param message 失败原因
     * @param error java异常信息
     * @param <T> 响应对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> fail(String message, String error) {
        return new ResultVO(message, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 构建指定httpstatus的失败Result
     *
     * @param message 失败原因
     * @param httpStatus httpStatus
     * @param <T> 响应对象
     * @return ResultVO
     */
    @Deprecated
    public static <T> ResultVO<T> fail(String message, HttpStatus httpStatus) {
        return new ResultVO(message, null, httpStatus);
    }

    /**
     * 构建指定httpstatus的失败Result
     *
     * @param message 失败原因
     * @param error java异常信息
     * @param httpStatus httpstatus
     * @param <T> 响应对象
     * @return ResultVO
     */
    @Deprecated
    public static <T> ResultVO<T> fail(String message, String error, HttpStatus httpStatus) {
        return new ResultVO(message, error, httpStatus);
    }

    /**
     * 构建指定httpstatus的Result
     *
     * @param message 提示信息
     * @param httpStatus httpstatus
     * @param <T> 响应对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> status(String message, HttpStatus httpStatus) {
        return new ResultVO(message, null, httpStatus);
    }

    /**
     * 构建指定httpstatus的Result
     *
     * @param message 失败原因
     * @param error java异常信息
     * @param httpStatus httpstatus
     * @param <T> 响应对象
     * @return ResultVO
     */
    public static <T> ResultVO<T> status(String message, String error, HttpStatus httpStatus) {
        return new ResultVO(message, error, httpStatus);
    }

    /**
     * 创建响应result对象
     */
    private ResultVO() {
        this.setHttpStatus(HttpStatus.OK);
        this.setStatus(HttpStatus.OK.value());
    }

    /**
     * 创建响应result对象
     *
     * @param data 数据对象
     */
    private ResultVO(T data) {
        this.setHttpStatus(HttpStatus.OK);
        this.setStatus(HttpStatus.OK.value());
        this.setData(data);
    }

    /**
     * 创建响应失败result对象
     *
     * @param httpStatus http状态码
     * @param message    失败原因描述
     * @param error      抛出异常信息
     */
    private ResultVO(String message, String error, HttpStatus httpStatus) {
        this.setMsg(message);
        this.setError(error);
        this.setHttpStatus(httpStatus);
    }

}
