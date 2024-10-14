package com.gstool.common.controller;

import com.gstool.common.model.base.ResultDTO;
import com.gstool.common.model.entity.PcPspCockpitMvlineDTO;
import com.gstool.common.model.query.PcPspCockpitMvlineQuery;
import com.gstool.common.service.PcPspCockpitMvlineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/PcPspCockpitMvline")
public class PcPspCockpitMvlineController {

private final PcPspCockpitMvlineService pcPspCockpitMvlineService;

    @PostMapping("/add")
    public ResultDTO<Object> add(@RequestBody PcPspCockpitMvlineDTO dto) {
    pcPspCockpitMvlineService.saveOrUpdate(dto);
        return ResultDTO.ok();
    }

    @PostMapping("/update")
    public ResultDTO<Object> update(@RequestBody PcPspCockpitMvlineDTO dto) {
        pcPspCockpitMvlineService.updateById(dto);
        return ResultDTO.ok();
    }

    @GetMapping("/delete/{id}")
    public ResultDTO<Object> delete(@PathVariable("id") String id) {
        boolean isDeleted = pcPspCockpitMvlineService.removeById(id);
        if (isDeleted) {
            return ResultDTO.ok();
        }else{
            return ResultDTO.error();
        }
    }

    @PostMapping("/delete")
    public ResultDTO<Object> deleteBatchIds(@RequestBody List<String> list) {
        pcPspCockpitMvlineService.removeBatchByIds(list);
        return ResultDTO.ok();
    }

    @PostMapping("/queryList")
    public ResultDTO<Object> test(@RequestBody PcPspCockpitMvlineQuery query) {
        return ResultDTO.success("成功", pcPspCockpitMvlineService.list(query));
    }

    @PostMapping("/page")
    public ResultDTO<Object> page(@RequestBody PcPspCockpitMvlineQuery query) {
        return ResultDTO.success("成功", pcPspCockpitMvlineService.page(query));
    }

}