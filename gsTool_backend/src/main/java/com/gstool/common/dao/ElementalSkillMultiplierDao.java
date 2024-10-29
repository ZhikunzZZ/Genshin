package com.gstool.common.dao;

import com.gstool.common.model.entity.ElementalSkillMultiplierDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ElementalSkillMultiplierDao {

    @Select("SELECT * FROM ELEMENTAL_SKILL_MULTIPLIER WHERE ID = #{id}")
    ElementalSkillMultiplierDTO findByName(@Param("id") String id);
}
