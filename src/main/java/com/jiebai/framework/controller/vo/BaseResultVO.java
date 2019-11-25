package com.jiebai.framework.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 基础响应结果
 *
 * @author lizhihui
 * @version 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BaseResultVO {

    @JsonIgnore
    @JSONField(serialize = false)
    private HttpStatus httpStatus;

    private Integer status;

    private String msg;

    private String error;

    private String path;

    private Long timestamp;

    public int getStatus() {
        return this.httpStatus.value();
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

}
