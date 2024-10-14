package com.gstool.common.service;

import com.gstool.common.model.entity.${className}DTO;
import com.gstool.common.model.query.${className}Query;
import com.gstool.common.model.vo.${className}VO;
import com.gstool.common.model.base.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ${className}Service extends IService<${className}DTO> {

    List<${className}VO> list(${className}Query query);

    PageResult<${className}VO> page(${className}Query query);

}