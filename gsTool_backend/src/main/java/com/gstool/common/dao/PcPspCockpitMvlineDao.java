package com.gstool.common.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gstool.common.model.entity.PcPspCockpitMvlineDTO;
import com.gstool.common.model.query.PcPspCockpitMvlineQuery;
import com.gstool.common.model.vo.PcPspCockpitMvlineVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PcPspCockpitMvlineDao extends BaseMapper<PcPspCockpitMvlineDTO> {

    List<PcPspCockpitMvlineVO> queryList(@Param("query") PcPspCockpitMvlineQuery query);

    IPage<PcPspCockpitMvlineVO> queryList(IPage<PcPspCockpitMvlineDTO> page, @Param("query") PcPspCockpitMvlineQuery query);

}