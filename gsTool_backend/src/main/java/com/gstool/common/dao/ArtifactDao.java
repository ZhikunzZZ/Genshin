package com.gstool.common.dao;

import com.gstool.common.model.entity.ArtifactDTO;
import org.apache.ibatis.annotations.Delete;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArtifactDao extends BaseMapper<ArtifactDTO> {

    @Delete("DELETE FROM ARTIFACT WHERE USER_ID = #{userId}")
    void deleteArtifactsByUserId(String userId);

    @Select("SELECT * FROM ARTIFACT WHERE LEVEL = 20 AND USER_ID = #{userId} AND POSITION = #{position} AND MAIN_TAG_NAME = #{mainTag}")
    List<ArtifactDTO> findByPositionAndMainTag(@Param("userId") String userId,
                                               @Param("position") String position, @Param("mainTag") String mainTag);

}