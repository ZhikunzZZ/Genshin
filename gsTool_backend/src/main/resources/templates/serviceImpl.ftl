package com.gstool.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gstool.common.dao.${className}Dao;
import com.gstool.common.model.entity.${className}DTO;
import com.gstool.common.model.query.${className}Query;
import com.gstool.common.model.vo.${className}VO;
import com.gstool.common.service.${className}Service;
import com.gstool.common.model.base.PageResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ${className}ServiceImpl extends ServiceImpl<${className}Dao, ${className}DTO> implements ${className}Service {

    @Override
    public List<${className}VO> list(${className}Query query) {

        return baseMapper.queryList(query);
    }

    @Override
    public PageResult<${className}VO> page(${className}Query query) {

        IPage<${className}DTO> page = new Page<>(query.getPageNo(), query.getPageSize());
        IPage<${className}VO> list = baseMapper.queryList(page, query);
        return new PageResult<>(list.getRecords(), list.getTotal(), list.getSize(), list.getCurrent());

    }

}