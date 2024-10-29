package com.gstool.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ELEMENTAL_SKILL_MULTIPLIER")
public class ElementalSkillMultiplierDTO {

    private Double lv3;

    private String hitIndex;

    private Double lv4;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Double lv12;

    private Double lv2;

    private String characterId;

    private Double lv1;

    private Double lv14;

    private Double lv9;

    private Double lv11;

    private Double lv7;

    private Double lv8;

    private Double lv6;

    private Double lv13;

    private Double lv10;

    private Double lv5;

}