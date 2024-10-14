package com.gstool.common.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gstool.common.model.entity.TestDTO;
import com.gstool.common.model.query.TestQuery;
import com.gstool.common.model.vo.TestVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TestDao extends BaseMapper<TestDTO> {

    List<TestVO> queryList(@Param("query") TestQuery query);

    IPage<TestVO> queryList(IPage<TestDTO> page, @Param("query") TestQuery query);

}