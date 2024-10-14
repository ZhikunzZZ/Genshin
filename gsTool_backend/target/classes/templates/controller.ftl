package com.gstool.common.controller;

import com.gstool.common.model.base.ResultDTO;
import com.gstool.common.model.entity.${className}DTO;
import com.gstool.common.model.query.${className}Query;
import com.gstool.common.service.${className}Service;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/${className}")
public class ${className}Controller {

private final ${className}Service ${classNameCamelCase}Service;

    @PostMapping("/add")
    public ResultDTO<Object> add(@RequestBody ${className}DTO dto) {
        ${classNameCamelCase}Service.saveOrUpdate(dto);
        return ResultDTO.ok();
    }

    @PostMapping("/update")
    public ResultDTO<Object> update(@RequestBody ${className}DTO dto) {
        ${classNameCamelCase}Service.updateById(dto);
        return ResultDTO.ok();
    }

    @GetMapping("/delete/{id}")
    public ResultDTO<Object> delete(@PathVariable("id") String id) {
        boolean isDeleted = ${classNameCamelCase}Service.removeById(id);
        if (isDeleted) {
            return ResultDTO.ok();
        }else{
            return ResultDTO.error();
        }
    }

    @PostMapping("/delete")
    public ResultDTO<Object> deleteBatchIds(@RequestBody List<String> list) {
        ${classNameCamelCase}Service.removeBatchByIds(list);
        return ResultDTO.ok();
    }

    @PostMapping("/queryList")
    public ResultDTO<Object> test(@RequestBody ${className}Query query) {
        return ResultDTO.success("成功", ${classNameCamelCase}Service.list(query));
    }

    @PostMapping("/page")
    public ResultDTO<Object> page(@RequestBody ${className}Query query) {
        return ResultDTO.success("成功", ${classNameCamelCase}Service.page(query));
    }

}