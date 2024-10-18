package com.gstool.common.dao;

import com.gstool.common.model.entity.CharacterDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CharacterDao {

    @Select("SELECT * FROM CHARACTER WHERE name = #{name}")
    CharacterDTO findByName(@Param("name") String name);
}
