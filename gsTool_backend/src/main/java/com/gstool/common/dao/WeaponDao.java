package com.gstool.common.dao;

import com.gstool.common.model.entity.WeaponDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WeaponDao {

    @Select("SELECT * FROM WEAPON WHERE name = #{name}")
    WeaponDTO findByName(@Param("name") String name);
}