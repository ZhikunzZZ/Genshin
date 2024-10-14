package com.gstool.common.service;

import com.gstool.common.model.entity.TestDTO;
import com.gstool.common.model.query.TestQuery;
import com.gstool.common.model.vo.TestVO;
import com.gstool.common.model.base.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService extends IService<TestDTO> {

    List<TestVO> list(TestQuery query);

    PageResult<TestVO> page(TestQuery query);

}