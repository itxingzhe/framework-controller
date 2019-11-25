package com.jiebai.framework.controller.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页响应结果对象
 *
 * @author lizhihui
 * @version 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PageVO<T> {

    private Long total;

    private Integer pages;

    private Boolean hasNextPage;

    private List<T> list;

    public static <T> PageVO<T> init(PageInfo<T> dataPageInfo) {
        return new PageVO<>(dataPageInfo);
    }

    /**
     * 创建响应失败result对象
     *
     * @param dataPageInfo 数据分页对象
     */
    public PageVO(PageInfo<T> dataPageInfo) {
        this.setList(dataPageInfo.getList());
        this.total = dataPageInfo.getTotal();
        this.pages = dataPageInfo.getPages();
        this.hasNextPage = dataPageInfo.isHasNextPage();
    }
}
