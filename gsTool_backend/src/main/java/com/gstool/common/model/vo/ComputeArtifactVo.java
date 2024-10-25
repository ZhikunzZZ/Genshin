package com.gstool.common.model.vo;

import com.gstool.common.model.entity.ArtifactDTO;
import lombok.Data;

@Data
public class ComputeArtifactVo {

    private String computeParam;
    private double damage;
    private double attack;
    private double hp;
    private double defend;
    private double criticalRate;
    private double criticalDamage;
    private double elementalMastery;
    private double energyRecharge;

    private double fireBonus;
    private double waterBonus;
    private double dendroBonus;
    private double thunderBonus;
    private double windBonus;
    private double iceBonus;
    private double rockBonus;
    private double physicalBonus;

    private ArtifactDTO flower;
    private ArtifactDTO feather;
    private ArtifactDTO sand;
    private ArtifactDTO cup;
    private ArtifactDTO head;

}
