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
@TableName("CHARACTER")
public class CharacterDTO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;


    private Double defend;

    private Integer star;

    private String weaponType;

    private String name;

    private Double elementalMastery;

    private Double criticalRate;

    private Double criticalDmg;

    private Double attack;

    private Double recharge;

    private Double life;

}