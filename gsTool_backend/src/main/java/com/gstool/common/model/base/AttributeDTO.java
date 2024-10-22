package com.gstool.common.model.base;

import lombok.Data;

@Data
public class AttributeDTO {

    private Double hp;
    private Double attack;
    private Double defense;
    private Double elementalMastery;
    private Double criticalRate;
    private Double criticalDamage;
    private Double healingBonus;
    private Double energyRecharge;
    private Double shieldStrength;
    //火元素
    private Double pyroDamageBonus;
    //水元素
    private Double hydroDamageBonus;
    //草元素
    private Double dendroDamageBonus;
    //雷元素
    private Double electroDamageBonus;
    //风元素
    private Double anemoDamageBouns;
    //冰元素
    private Double cryoDamageBonus;
    //岩元素
    private Double geoDamageBonus;
    //物理
    private Double physicalDamageBonus;

}
