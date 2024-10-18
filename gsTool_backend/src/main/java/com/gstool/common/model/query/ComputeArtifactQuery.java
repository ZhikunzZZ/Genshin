package com.gstool.common.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputeArtifactQuery {

    private String character;

    private Integer constellation;

    private String weapon;

    private Integer WeaponRefinementLevel;

    private Integer normaLAttackLevel;

    private Integer elementalSkillLevel;

    private Integer elementalBurstLevel;

    private List<String> buffs;

    private String computeParam;



}
