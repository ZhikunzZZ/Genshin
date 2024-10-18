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
@TableName("NORMAL_ATTACK_MULTIPLIER")
public class NormalAttackMultiplierDTO {

    private String characterId;

    private String hitIndex;

    private Double lv14;

    private Double lv8;

    private Double lv7;

    private Double lv6;

    private Double lv5;

    private Double lv4;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Double lv10;

    private Double lv3;

    private Double lv2;

    private Double lv11;

    private Double lv12;

    private Double lv1;

    private Double lv9;

    private Double lv13;

}