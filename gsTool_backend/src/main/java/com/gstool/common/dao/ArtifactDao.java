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

    @Select({
            "<script>",
            "SELECT * FROM ARTIFACT WHERE LEVEL = 20 AND USER_ID = #{userId} AND POSITION = #{position}",
            "AND (",
            "<if test='mainTag1 != null'> MAIN_TAG_NAME = #{mainTag1}</if>",
            "<if test='mainTag2 != null'> OR MAIN_TAG_NAME = #{mainTag2}</if>",
            "<if test='mainTag3 != null'> OR MAIN_TAG_NAME = #{mainTag3}</if>",
            "<if test='mainTag4 != null'> OR MAIN_TAG_NAME = #{mainTag4}</if>",
            ")",
            "</script>"
    })
    List<ArtifactDTO> findByPositionAndMainTag(@Param("userId") String userId, @Param("position") String position,
                                               @Param("mainTag1") String mainTag1, @Param("mainTag2") String mainTag2,
                                               @Param("mainTag3") String mainTag3, @Param("mainTag4") String mainTag4);

}