package com.gstool.common.service.impl;

import com.gstool.common.dao.ArtifactDao;
import com.gstool.common.dao.CharacterDao;
import com.gstool.common.dao.NormalAttackMultiplierDao;
import com.gstool.common.dao.WeaponDao;
import com.gstool.common.model.base.ArtifactListDTO;
import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.base.AttributeDTO;
import com.gstool.common.model.entity.ArtifactDTO;
import com.gstool.common.model.entity.CharacterDTO;
import com.gstool.common.model.entity.NormalAttackMultiplierDTO;
import com.gstool.common.model.entity.WeaponDTO;
import com.gstool.common.model.query.ComputeArtifactQuery;
import com.gstool.common.service.ComputeArtifactService;
import com.gstool.common.service.method.BaseGetMethod;
import com.gstool.common.service.method.BaseSetMethod;
import com.gstool.common.service.method.targetFunction.fire.ArlecchinoFunction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComputeArtifactServiceImpl implements ComputeArtifactService {

    private CharacterDao characterDao;
    private WeaponDao weaponDao;
    private NormalAttackMultiplierDao normalAttackMultiplierDao;
    private ArtifactDao artifactDao;
    private BaseGetMethod baseGetMethod;
    private ArlecchinoFunction arlecchinoFunction;
    private BaseSetMethod baseSetMethod;

    @Override
    public void computeArtifact(ComputeArtifactQuery query){

        CharacterDTO character = characterDao.findByName(query.getCharacter());
        WeaponDTO weapon = weaponDao.findByName(query.getWeapon());
        AttributeAndMultiplierZoneDTO a = new AttributeAndMultiplierZoneDTO();

        //数值初始化（计算角色90级初始数值+武器主词条）
        double baseAttack = character.getAttack() + weapon.getBaseAttack();
        double baseHp = character.getLife();
        double baseDefend = character.getDefend();
        double baseElementalMastery = character.getElementalMastery();
        double baseRecharge = character.getRecharge();
        double baseCriticalRate = character.getCriticalRate();
        double baseCriticalDmg = character.getCriticalDmg();

        a.setAttack(baseAttack);
        a.setHp(baseHp);
        a.setDefense(baseDefend);
        a.setElementalMastery(baseElementalMastery);
        a.setEnergyRecharge(baseRecharge);
        a.setCriticalRate(baseCriticalRate);
        a.setCriticalDamage(baseCriticalDmg);
        //治疗加成与护盾强度（暂时无用）
        a.setHealingBonus(0.0);
        a.setShieldStrength(0.0);
        //火元素
        a.setPyroDamageBonus(0.0);
        //水元素
        a.setHydroDamageBonus(0.0);
        //草元素
        a.setDendroDamageBonus(0.0);
        //雷元素
        a.setElectroDamageBonus(0.0);
        //风元素
        a.setAnemoDamageBouns(0.0);
        //冰元素
        a.setCryoDamageBonus(0.0);
        //岩元素
        a.setGeoDamageBonus(0.0);
        //物理
        a.setPhysicalDamageBonus(0.0);

        a.setBaseDamageMultiplierZone(0.0);
//        private Double criticalMultiplierZone;

        //加成乘区
        a.setBonusDamageMultiplierZone(0.0);
        //防御乘区
        a.setDefenseMultiplierZone(0.5);
        //抗性乘区
        a.setResistanceMultiplierZone(0.9);

        //加入角色自身数值（包括天赋，命座，普攻或技能倍率）
        AttributeAndMultiplierZoneDTO c_result = arlecchinoFunction.calculateMultipliers(weapon, character, query.getConstellation(),
                query.getNormaLAttackLevel(), query.getElementalSkillLevel(), query.getElementalBurstLevel(), query.getComputeParam());

        a.setBaseDamageMultiplierZone(a.getBaseDamageMultiplierZone() + c_result.getBaseDamageMultiplierZone());
        a.setBonusDamageMultiplierZone(a.getBonusDamageMultiplierZone() + c_result.getBonusDamageMultiplierZone());
        a.setCriticalRate(a.getCriticalRate() + c_result.getCriticalRate());
        a.setCriticalDamage(a.getCriticalDamage() + c_result.getCriticalDamage());

        //加入武器副词条以及武器被动
        baseSetMethod.AddStatAndPassiveEffect(a, weapon);

        //筛选有效圣遗物
        ArtifactListDTO targetList = baseGetMethod.getTargetArtifactList(character.getId(), "111");

        List<ArtifactDTO> flowerList = targetList.getFlowerList();
        List<ArtifactDTO> featherList = targetList.getFeatherList();
        List<ArtifactDTO> sandList = targetList.getSandList();
        List<ArtifactDTO> cupList = targetList.getCupList();
        List<ArtifactDTO> headList = targetList.getHeadList();

        System.out.println(flowerList.size());
        System.out.println(featherList.size());
        System.out.println(sandList.size());
        System.out.println(cupList.size());
        System.out.println(headList.size());

        double max_dmg = 0.0;
        double max_attack = 0.0;
        double max_critRate = 0.0;
        double max_critDmg = 0.0;
        double max_fireBonus = 0.0;
        ArtifactDTO flower_max = null;
        ArtifactDTO feather_max = null;
        ArtifactDTO sand_max = null;
        ArtifactDTO cup_max = null;
        ArtifactDTO head_max = null;
//        int i = 0;

        for (ArtifactDTO flower: flowerList) {
            for (ArtifactDTO feather: featherList) {
                for (ArtifactDTO sand: sandList) {
                    for (ArtifactDTO cup: cupList) {
                        for (ArtifactDTO head: headList) {

                            Boolean isMatching4 = isMatchingSet(flower, feather, sand, cup, head, "FragmentOfHarmonicWhimsy", 4);
                            Boolean isMatching2 = isMatchingSet(flower, feather, sand, cup, head, "FragmentOfHarmonicWhimsy", 2);

                            double attack = a.getAttack();
                            double critRate = a.getCriticalRate();
                            double critDmg = a.getCriticalDamage();
                            double fireBonus = a.getPyroDamageBonus();

                            attack += (getMainStatValue(sand, "attackPercentage") + getMainStatValue(cup, "attackPercentage")
                                    + getMainStatValue(head, "attackPercentage") + getSubStatValue(flower, "attackPercentage")
                                    + getSubStatValue(feather, "attackPercentage") + getSubStatValue(sand, "attackPercentage")
                                    + getSubStatValue(cup, "attackPercentage") + getSubStatValue(head, "attackPercentage")) * baseAttack;

                            attack += getMainStatValue(feather, "attackStatic") + getSubStatValue(flower, "attackStatic")
                                    + getSubStatValue(feather, "attackStatic") + getSubStatValue(sand, "attackStatic")
                                    + getSubStatValue(cup, "attackStatic") + getSubStatValue(head, "attackStatic");

                            critRate += getMainStatValue(head, "critical") + getSubStatValue(flower, "critical")
                                    + getSubStatValue(feather, "critical") + getSubStatValue(sand, "critical")
                                    + getSubStatValue(cup, "critical") + getSubStatValue(head, "critical");

                            critDmg += getMainStatValue(head, "criticalDamage") + getSubStatValue(flower, "criticalDamage")
                                    + getSubStatValue(feather, "criticalDamage") + getSubStatValue(sand, "criticalDamage")
                                    + getSubStatValue(cup, "criticalDamage") + getSubStatValue(head, "criticalDamage");

                            fireBonus += getMainStatValue(cup, "fireBonus");

                            double hope_dmg = 0.0;


                            if(isMatching2){
                                attack += 0.18 * baseAttack;
                            }

                            double crit_part = (1 + critDmg) * (critRate) + 1 * (1 - critRate);

                            if(isMatching4){
                                hope_dmg = (a.getBaseDamageMultiplierZone() * attack) * (1 + a.getBonusDamageMultiplierZone() + 0.54 + fireBonus) * crit_part * a.getDefenseMultiplierZone() * a.getResistanceMultiplierZone();

                            }else {
                                hope_dmg = (a.getBaseDamageMultiplierZone() * attack) * (1 + a.getBonusDamageMultiplierZone() + fireBonus) * crit_part * a.getDefenseMultiplierZone() * a.getResistanceMultiplierZone();
                            }

//                            i++;
//                            System.out.println(i);

                            if(hope_dmg > max_dmg){

                                max_dmg = hope_dmg;
                                max_attack = attack;
                                max_critRate = critRate;
                                max_critDmg = critDmg;
                                max_fireBonus = fireBonus;
                                flower_max = flower;
                                feather_max = feather;
                                sand_max = sand;
                                cup_max = cup;
                                head_max = head;


                            }
                        }
                    }
                }
            }
        }
        System.out.println(max_dmg);
        System.out.println(max_attack);
        System.out.println(max_critRate);
        System.out.println(max_critDmg);
        System.out.println(max_fireBonus);
        printA(flower_max);
        printA(feather_max);
        printA(sand_max);
        printA(cup_max);
        printA(head_max);
    }

    private void printA(ArtifactDTO dto){

        System.out.println("--------------");
        System.out.println(dto.getId());
        System.out.println(dto.getSetName());
        System.out.println(dto.getMainTagName() + " - " + dto.getMainTagValue());
        System.out.println(dto.getFirstNormalTagName() + " - " + dto.getFirstNormalTagValue());
        System.out.println(dto.getSecondNormalTagName() + " - " + dto.getSecondNormalTagValue());
        System.out.println(dto.getThirdNormalTagName() + " - " + dto.getThirdNormalTagValue());
        System.out.println(dto.getForthNormalTagName() + " - " + dto.getForthNormalTagValue());

    }

    private boolean isMatchingSet(ArtifactDTO flower, ArtifactDTO feather, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, String targetSetName, Integer num) {
        int matchCount = 4 - num;

        // 检查每个部位的 setName 是否为 targetSetName
        if (targetSetName.equals(flower.getSetName())) {
            matchCount++;
        }
        if (targetSetName.equals(feather.getSetName())) {
            matchCount++;
        }
        if (targetSetName.equals(sand.getSetName())) {
            matchCount++;
        }
        if (targetSetName.equals(cup.getSetName())) {
            matchCount++;
        }
        if (targetSetName.equals(head.getSetName())) {
            matchCount++;
        }
        return matchCount >= 4;
    }

    private double getMainStatValue(ArtifactDTO artifact, String statName) {
        if (artifact.getMainTagName().equals(statName)) {
            return artifact.getMainTagValue();
        }
        return 0.0;
    }

    private double getSubStatValue(ArtifactDTO artifact, String statName) {
        double totalValue = 0.0;
        if (artifact.getFirstNormalTagName().equals(statName)) {
            totalValue += artifact.getFirstNormalTagValue();
        }
        if (artifact.getSecondNormalTagName().equals(statName)) {
            totalValue += artifact.getSecondNormalTagValue();
        }
        if (artifact.getThirdNormalTagName().equals(statName)) {
            totalValue += artifact.getThirdNormalTagValue();
        }
        if (artifact.getForthNormalTagName().equals(statName)) {
            totalValue += artifact.getForthNormalTagValue();
        }
        return totalValue;
    }



}
