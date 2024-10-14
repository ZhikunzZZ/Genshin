package com.gstool.common.controller;

import com.gstool.common.model.base.ResultDTO;
import com.gstool.common.model.entity.TestDTO;
import com.gstool.common.model.query.TestQuery;
import com.gstool.common.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Test")
public class TestController {

private final TestService testService;

    @PostMapping("/add")
    public ResultDTO<Object> add(@RequestBody TestDTO dto) {
        testService.saveOrUpdate(dto);
        return ResultDTO.ok();
    }

    @PostMapping("/update")
    public ResultDTO<Object> update(@RequestBody TestDTO dto) {
        testService.updateById(dto);
        return ResultDTO.ok();
    }

    @GetMapping("/delete/{id}")
    public ResultDTO<Object> delete(@PathVariable("id") String id) {
        boolean isDeleted = testService.removeById(id);
        if (isDeleted) {
            return ResultDTO.ok();
        }else{
            return ResultDTO.error();
        }
    }

    @PostMapping("/delete")
    public ResultDTO<Object> deleteBatchIds(@RequestBody List<String> list) {
        testService.removeBatchByIds(list);
        return ResultDTO.ok();
    }

    @PostMapping("/queryList")
    public ResultDTO<Object> test(@RequestBody TestQuery query) {
        return ResultDTO.success("成功", testService.list(query));
    }

    @PostMapping("/page")
    public ResultDTO<Object> page(@RequestBody TestQuery query) {
        return ResultDTO.success("成功", testService.page(query));
    }

}