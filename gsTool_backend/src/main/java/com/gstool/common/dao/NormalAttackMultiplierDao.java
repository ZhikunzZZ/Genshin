package com.gstool.common.dao;

import com.gstool.common.model.entity.NormalAttackMultiplierDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NormalAttackMultiplierDao {

    @Select("SELECT * FROM NORMAL_ATTACK_MULTIPLIER WHERE CHARACTER_ID = #{characterId}")
    List<NormalAttackMultiplierDTO> findByName(@Param("characterId") String characterId);
}
