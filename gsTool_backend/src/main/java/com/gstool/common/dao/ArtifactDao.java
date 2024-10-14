package com.gstool.common.dao;

import com.gstool.common.model.entity.ArtifactDTO;
import org.apache.ibatis.annotations.Delete;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
public interface ArtifactDao extends BaseMapper<ArtifactDTO> {

    @Delete("DELETE FROM ARTIFACT WHERE USER_ID = #{userId}")
    void deleteArtifactsByUserId(String userId);
}