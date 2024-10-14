package com.gstool.common.dao;

import com.gstool.common.model.entity.ArtifactDTO;
import org.apache.ibatis.annotations.Delete;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
public interface ArtifactMapper extends BaseMapper<ArtifactDTO> {

    // 删除某个 userId 下的所有圣遗物
    @Delete("DELETE FROM ARTIFACT WHERE USER_ID = #{userId}")
    void deleteArtifactsByUserId(String userId);
}