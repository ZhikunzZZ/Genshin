package com.gstool.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gstool.common.dao.PcPspCockpitMvlineDao;
import com.gstool.common.model.entity.PcPspCockpitMvlineDTO;
import com.gstool.common.model.query.PcPspCockpitMvlineQuery;
import com.gstool.common.model.vo.PcPspCockpitMvlineVO;
import com.gstool.common.service.PcPspCockpitMvlineService;
import com.gstool.common.model.base.PageResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PcPspCockpitMvlineServiceImpl extends ServiceImpl<PcPspCockpitMvlineDao, PcPspCockpitMvlineDTO> implements PcPspCockpitMvlineService {

    @Override
    public List<PcPspCockpitMvlineVO> list(PcPspCockpitMvlineQuery query) {

        return baseMapper.queryList(query);
    }

    @Override
    public PageResult<PcPspCockpitMvlineVO> page(PcPspCockpitMvlineQuery query) {

        IPage<PcPspCockpitMvlineDTO> page = new Page<>(query.getPageNo(), query.getPageSize());
        IPage<PcPspCockpitMvlineVO> list = baseMapper.queryList(page, query);
        return new PageResult<>(list.getRecords(), list.getTotal(), list.getSize(), list.getCurrent());

    }

}