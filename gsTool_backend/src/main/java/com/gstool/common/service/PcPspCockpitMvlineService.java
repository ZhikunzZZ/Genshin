package com.gstool.common.service;

import com.gstool.common.model.entity.PcPspCockpitMvlineDTO;
import com.gstool.common.model.query.PcPspCockpitMvlineQuery;
import com.gstool.common.model.vo.PcPspCockpitMvlineVO;
import com.gstool.common.model.base.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PcPspCockpitMvlineService extends IService<PcPspCockpitMvlineDTO> {

    List<PcPspCockpitMvlineVO> list(PcPspCockpitMvlineQuery query);

    PageResult<PcPspCockpitMvlineVO> page(PcPspCockpitMvlineQuery query);

}