package com.gstool.common.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gstool.common.model.entity.${className}DTO;
import com.gstool.common.model.query.${className}Query;
import com.gstool.common.model.vo.${className}VO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ${className}Dao extends BaseMapper<${className}DTO> {

    List<${className}VO> queryList(@Param("query") ${className}Query query);

    IPage<${className}VO> queryList(IPage<${className}DTO> page, @Param("query") ${className}Query query);

}