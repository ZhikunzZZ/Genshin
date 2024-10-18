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
@TableName("WEAPON")
public class WeaponDTO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Double secondaryStatValue;

    private String description;

    private String name;

    private Integer baseAttack;

    private Double passiveEffectValue1;

    private String passiveEffect2;

    private Integer rarity;

    private String secondaryStatName;

    private String passiveEffect3;

    private String passiveEffect1;

    private Double passiveEffectValue3;

    private String type;

    private Double passiveEffectValue2;

}