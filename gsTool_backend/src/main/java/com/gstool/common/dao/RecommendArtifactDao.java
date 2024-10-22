package com.gstool.common.dao;

import com.gstool.common.model.entity.RecommendArtifactDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RecommendArtifactDao {

    @Select("SELECT * FROM RECOMMEND_ARTIFACT WHERE ID = #{id}")
    RecommendArtifactDTO findById(@Param("id") String id);

}
