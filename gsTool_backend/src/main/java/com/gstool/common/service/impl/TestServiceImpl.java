package com.gstool.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gstool.common.dao.TestDao;
import com.gstool.common.model.entity.TestDTO;
import com.gstool.common.model.query.TestQuery;
import com.gstool.common.model.vo.TestVO;
import com.gstool.common.service.TestService;
import com.gstool.common.model.base.PageResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl extends ServiceImpl<TestDao, TestDTO> implements TestService {


    @Override
    public List<TestVO> list(TestQuery query) {

        return baseMapper.queryList(query);
    }

    @Override
    public PageResult<TestVO> page(TestQuery query) {

        IPage<TestDTO> page = new Page<>(query.getPageNo(), query.getPageSize());

        IPage<TestVO> list = baseMapper.queryList(page, query);

        return new PageResult<>(list.getRecords(), list.getTotal(), list.getSize(), list.getCurrent());

    }

}