package com.gstool.common.model.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  分页工具类
 *
 * @author Ivan
 * @since 2023/3/27
 **/
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int total;

    private int size;

    private int current;

    private List<T> list;


    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     *
     */
    public PageResult(List<T> list, long total, long size, long current) {
        this.list = list;
        this.total = (int)total;
        this.size = (int)size;
        this.current = (int)current;
    }
}
