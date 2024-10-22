package com.gstool.common.model.base;

import lombok.Data;

@Data
public class AttributeAndMultiplierZoneDTO extends AttributeDTO {
    //基础伤害乘区
    private Double baseDamageMultiplierZone;
    //暴击乘区
    private Double criticalMultiplierZone;
    //加成乘区
    private Double bonusDamageMultiplierZone;
    //防御乘区
    private Double defenseMultiplierZone;
    //抗性乘区
    private Double resistanceMultiplierZone;
}
