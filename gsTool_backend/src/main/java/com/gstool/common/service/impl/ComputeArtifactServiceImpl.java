package com.gstool.common.service.impl;

import com.gstool.common.dao.CharacterDao;
import com.gstool.common.dao.WeaponDao;
import com.gstool.common.model.base.ArtifactListDTO;
import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.entity.ArtifactDTO;
import com.gstool.common.model.entity.CharacterDTO;
import com.gstool.common.model.entity.WeaponDTO;
import com.gstool.common.model.query.ComputeArtifactQuery;
import com.gstool.common.model.vo.ComputeArtifactVo;
import com.gstool.common.service.ComputeArtifactService;
import com.gstool.common.service.method.BaseGetMethod;
import com.gstool.common.service.method.BaseSetMethod;
import com.gstool.common.service.method.BuffCalculator;
import com.gstool.common.service.method.characterFunction.FindCharacterFunction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class ComputeArtifactServiceImpl implements ComputeArtifactService {

    private CharacterDao characterDao;
    private WeaponDao weaponDao;
    private BaseGetMethod baseGetMethod;
    private BaseSetMethod baseSetMethod;
    private FindCharacterFunction findCharacterFunction;
    private BuffCalculator buffCalculator;

    @Override
    public ComputeArtifactVo computeArtifact(ComputeArtifactQuery query){

        CharacterDTO character = characterDao.findByName(query.getCharacter());
        WeaponDTO weapon = weaponDao.findByName(query.getWeapon());
        AttributeAndMultiplierZoneDTO a = new AttributeAndMultiplierZoneDTO();

        //数值初始化（计算角色90级初始数值+武器主词条）
        double baseAttack = character.getAttack() + weapon.getBaseAttack();
        double baseHp = character.getLife();
        double baseDefend = character.getDefend();

        a.setAttack(baseAttack);
        a.setHp(baseHp);
        a.setDefense(baseDefend);
        a.setElementalMastery(character.getElementalMastery());
        a.setEnergyRecharge(character.getRecharge());
        a.setCriticalRate(character.getCriticalRate());
        a.setCriticalDamage(character.getCriticalDmg());

        //治疗加成与护盾强度（暂时无用）
        a.setHealingBonus(character.getHealingBonus());
        a.setShieldStrength(character.getShieldStrength());

        a.setPyroDamageBonus(character.getPyroDamageBonus());               //火元素
        a.setHydroDamageBonus(character.getHydroDamageBonus());             //水元素
        a.setDendroDamageBonus(character.getDendroDamageBonus());           //草元素
        a.setElectroDamageBonus(character.getElectroDamageBonus());         //雷元素
        a.setAnemoDamageBonus(character.getAnemoDamageBonus());             //风元素
        a.setCryoDamageBonus(character.getCryoDamageBonus());               //冰元素
        a.setGeoDamageBonus(character.getGeoDamageBonus());                 //岩元素
        a.setPhysicalDamageBonus(character.getPhysicalDamageBonus());       //物理

        //基础伤害乘区
        a.setBaseDamageMultiplierZone(0.0);
        //加成乘区
        a.setBonusDamageMultiplierZone(1.0);
        //防御乘区（同等级默认为0.5）
        a.setDefenseMultiplierZone(0.5);
        //抗性
        a.setResistance(0.1);

        //加入角色自身数值（天赋，命座，普攻或技能倍率）
        findCharacterFunction.characterFunction(weapon, character, a, query.getNormaLAttackLevel(),
                query.getElementalSkillLevel(), query.getElementalBurstLevel(),
                query.getConstellation(), query.getComputeParam());

        //加入武器副词条以及武器被动
        baseSetMethod.AddStatAndPassiveEffect(a, weapon);

        //加入buffs
        buffCalculator.calculate(query.getBuffs(), a, baseAttack, baseHp, baseDefend);

        //筛选有效圣遗物
        ArtifactListDTO targetList = baseGetMethod.getTargetArtifactList(character.getId(), "111");

        List<ArtifactDTO> flowerList = targetList.getFlowerList();
        List<ArtifactDTO> featherList = targetList.getFeatherList();
        List<ArtifactDTO> sandList = targetList.getSandList();
        List<ArtifactDTO> cupList = targetList.getCupList();
        List<ArtifactDTO> headList = targetList.getHeadList();

//        System.out.println("flower list size: " + flowerList.size());
//        System.out.println("feather list size: " + featherList.size());
//        System.out.println("sand list size: " + sandList.size());
//        System.out.println("cup list size: " + cupList.size());
//        System.out.println("head list size: " + headList.size());

        AtomicReference<Double> max_dmg = new AtomicReference<>(0.0);
        AtomicReference<Double> max_attack = new AtomicReference<>(0.0);
        AtomicReference<Double> max_hp = new AtomicReference<>(0.0);
        AtomicReference<Double> max_defend = new AtomicReference<>(0.0);
        AtomicReference<Double> max_elementalMastery = new AtomicReference<>(0.0);
        AtomicReference<Double> max_energyRecharge = new AtomicReference<>(0.0);
        AtomicReference<Double> max_critRate = new AtomicReference<>(0.0);
        AtomicReference<Double> max_critDmg = new AtomicReference<>(0.0);

        AtomicReference<Double> max_fireBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_waterBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_dendroBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_thunderBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_windBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_iceBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_rockBonus = new AtomicReference<>(0.0);
        AtomicReference<Double> max_physicalBonus = new AtomicReference<>(0.0);

        AtomicReference<ArtifactDTO> flower_max = new AtomicReference<>(null);
        AtomicReference<ArtifactDTO> feather_max = new AtomicReference<>(null);
        AtomicReference<ArtifactDTO> sand_max = new AtomicReference<>(null);
        AtomicReference<ArtifactDTO> cup_max = new AtomicReference<>(null);
        AtomicReference<ArtifactDTO> head_max = new AtomicReference<>(null);

        //乘区
        double baseDamageMultiplierZone = a.getBaseDamageMultiplierZone();
        double bonusDamageMultiplierZone = a.getBonusDamageMultiplierZone();
        double defenseMultiplierZone = a.getDefenseMultiplierZone();
        double resistanceMultiplierZone;
        if (a.getResistance() < 0){
            resistanceMultiplierZone = 1 - (a.getResistance()) / 2;

        }else{
            resistanceMultiplierZone = 1 - a.getResistance();
        }
        a.setResistanceMultiplierZone(resistanceMultiplierZone);

        double attack = a.getAttack();
        double hp = a.getHp();
        double defense = a.getDefense();
        double elementalMastery = a.getElementalMastery();
        double energyRecharge = a.getEnergyRecharge();
        double critRate = a.getCriticalRate();
        double critDmg = a.getCriticalDamage();
        double fireBonus = a.getPyroDamageBonus();                      //火元素
        double waterBonus = a.getHydroDamageBonus();                    //水元素
        double dendroBonus = a.getDendroDamageBonus();                  //草元素
        double thunderBonus = a.getElectroDamageBonus();                //雷元素
        double windBonus = a.getAnemoDamageBonus();                    //风元素
        double iceBonus = a.getCryoDamageBonus();                      //冰元素
        double rockBonus = a.getGeoDamageBonus();                        //岩元素
        double physicalBonus = a.getPhysicalDamageBonus();              //物理

        Date start = new Date();

        cupList.parallelStream().forEach(cup -> {

            double attackP1 = getMainStatValue(cup, "attackPercentage") + getSubStatValue(cup, "attackPercentage");
            double attack1 = getSubStatValue(cup, "attackStatic") + attack;
            double hpP1 = getMainStatValue(cup, "lifePercentage") + getSubStatValue(cup, "lifePercentage");
            double hp1 = getSubStatValue(cup, "defendPercentage") + hp;
            double defendP1 = getMainStatValue(cup, "defendPercentage") + getSubStatValue(cup, "defendPercentage");
            double defend1 = getSubStatValue(cup, "defendStatic") + defense;
            double critRate1 = getSubStatValue(cup, "critical")  + critRate;
            double critDmg1 = getSubStatValue(cup, "criticalDamage")  + critDmg;
            double elementalMastery1 = getMainStatValue(cup, "elementalMastery") + getSubStatValue(cup, "elementalMastery") + elementalMastery;
            double energyRecharge1 = getSubStatValue(cup, "recharge") + energyRecharge;

            double fireBonusLocalSet = getMainStatValue(cup, "fireBonus") + fireBonus;
            double waterBonusLocalSet = getMainStatValue(cup, "waterBonus") + waterBonus;
            double dendroBonusLocalSet = getMainStatValue(cup, "dendroBonus") + dendroBonus;
            double thunderBonusLocalSet = getMainStatValue(cup, "thunderBonus") + thunderBonus;
            double windBonusLocalSet = getMainStatValue(cup, "windBonus") + windBonus;
            double iceBonusLocalSet = getMainStatValue(cup, "iceBonus") + iceBonus;
            double rockBonusLocalSet = getMainStatValue(cup, "rockBonus") + rockBonus;
            double physicalBonusLocalSet = getMainStatValue(cup, "physicalBonus") + physicalBonus;

            featherList.parallelStream().forEach(feather -> {

                double attackP2 = getSubStatValue(feather, "attackPercentage") + attackP1;
                double attack2 = getMainStatValue(feather, "attackStatic") + attack1;
                double hpP2 = getSubStatValue(feather, "lifePercentage") + hpP1;
                double hp2 = getSubStatValue(feather, "lifeStatic") + hp1;
                double defendP2 = getSubStatValue(feather, "defendPercentage") + defendP1;
                double defend2 = getSubStatValue(feather, "defendStatic") + defend1;
                double critRate2 = getSubStatValue(feather, "critical") + critRate1;
                double critDmg2 = getSubStatValue(feather, "criticalDamage") + critDmg1;
                double elementalMastery2 = getSubStatValue(feather, "elementalMastery") + elementalMastery1;
                double energyRecharge2 = getSubStatValue(feather, "recharge") + energyRecharge1;

                flowerList.parallelStream().forEach(flower -> {

                    double attackP3 = getSubStatValue(flower, "attackPercentage") + attackP2;
                    double attack3 = getSubStatValue(flower, "attackStatic") + attack2;
                    double hpP3 = getSubStatValue(flower, "flowerPercentage") + hpP2;
                    double hp3 = getMainStatValue(flower, "lifeStatic") + hp2;
                    double defendP3 = getSubStatValue(flower, "defendPercentage") + defendP2;
                    double defend3 = getSubStatValue(flower, "defendStatic") + defend2;
                    double critRate3 = getSubStatValue(flower, "critical") + critRate2;
                    double critDmg3 = getSubStatValue(flower, "criticalDamage") + critDmg2;
                    double elementalMastery3 = getSubStatValue(flower, "elementalMastery") + elementalMastery2;
                    double energyRecharge3 = getSubStatValue(flower, "recharge") + energyRecharge2;

                    sandList.parallelStream().forEach(sand -> {

                        double attackP4 = getMainStatValue(sand, "attackPercentage") + getSubStatValue(sand, "attackPercentage") + attackP3;
                        double attack4 = getSubStatValue(sand, "attackStatic") + attack3;
                        double hpP4 = getMainStatValue(sand, "lifePercentage") + getSubStatValue(sand, "lifePercentage") + hpP3;
                        double hp4 = getSubStatValue(sand, "lifeStatic") + hp3;
                        double defendP4 = getMainStatValue(sand, "defendPercentage") + getSubStatValue(sand, "defendPercentage") + defendP3;
                        double defend4 = getSubStatValue(sand, "defendStatic") + defend3;
                        double critRate4 = getSubStatValue(sand, "critical") + critRate3;
                        double critDmg4 = getSubStatValue(sand, "criticalDamage") + critDmg3;
                        double elementalMastery4 = getMainStatValue(sand, "elementalMastery") + getSubStatValue(sand, "elementalMastery") + elementalMastery3;
                        double energyRecharge4 = getMainStatValue(sand, "recharge") + getSubStatValue(sand, "recharge") + energyRecharge3;

                        headList.parallelStream().forEach(head -> {

                            double attackLocalSet = (getMainStatValue(head, "attackPercentage") + getSubStatValue(head, "attackPercentage") + attackP4) * baseAttack;
                            attackLocalSet += getSubStatValue(head, "attackStatic") + attack4;

                            double hpLocalSet = (getMainStatValue(head, "lifePercentage") + getSubStatValue(head, "lifePercentage") + hpP4) * baseHp;
                            hpLocalSet += getSubStatValue(head, "lifeStatic") + hp4;

                            double defendLocalSet = (getMainStatValue(head, "defendPercentage") + getSubStatValue(head, "defendPercentage") + defendP4) * baseDefend;
                            defendLocalSet += getSubStatValue(head, "defendStatic") + defend4;

                            double critRateLocalSet = getMainStatValue(head, "critical")+ getSubStatValue(head, "critical") + critRate4;
                            double critDmgLocalSet = getMainStatValue(head, "criticalDamage") + getSubStatValue(head, "criticalDamage") + critDmg4;

                            double elementalMasteryLocalSet = getMainStatValue(head, "elementalMastery") + getSubStatValue(head, "elementalMastery") + elementalMastery4;

                            double energyRechargeLocalSet = getSubStatValue(head, "recharge") + energyRecharge4;

                            double fire = fireBonusLocalSet;
                            double water = waterBonusLocalSet;
                            double dendro = dendroBonusLocalSet;
                            double thunder = thunderBonusLocalSet;
                            double wind = windBonusLocalSet;
                            double ice = iceBonusLocalSet;
                            double rock = rockBonusLocalSet;
                            double physical = physicalBonusLocalSet;

                            double baseDamageZone = baseDamageMultiplierZone;
                            double bonusDamageZone = bonusDamageMultiplierZone;
//                            double defenseZone = defenseMultiplierZone;
//                            double resistanceZone = resistanceMultiplierZone;

                            //加入圣遗物套装加成
                            Map<String, Integer> setCountMap = new HashMap<>();
                            ArtifactDTO[] artifacts = {flower, feather, sand, cup, head};
                            for (ArtifactDTO artifact : artifacts) {
                                String setName = artifact.getSetName();
                                setCountMap.put(setName, setCountMap.getOrDefault(setName, 0) + 1);
                            }
                            for (Map.Entry<String, Integer> entry : setCountMap.entrySet()) {
                                String setName = entry.getKey();
                                int count = entry.getValue();
                                if (count >= 4) {
                                    switch (setName) {
                                        case "FragmentOfHarmonicWhimsy":
                                            bonusDamageZone += 0.54;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                if (count >= 2) {
                                    switch (setName) {
                                        case "FragmentOfHarmonicWhimsy", "Gladiator's Finale", "Shimenawa's Reminiscence":
                                            attackLocalSet += 0.18 * baseAttack;
                                            break;
                                        case "Crimson Witch of Flames":
                                            fire += 0.15;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            //计算暴击乘区
                            double crit_part = (1 + critDmgLocalSet) * (critRateLocalSet) + 1 * (1 - critRateLocalSet);

                            //计算伤害
                            double hope_dmg = baseGetMethod.getHopeDamage(query.getComputeParam(), attackLocalSet,
                                    hpLocalSet, defendLocalSet, crit_part, elementalMasteryLocalSet, baseDamageZone,
                                    bonusDamageZone, defenseMultiplierZone, resistanceMultiplierZone, fire, water);

                            //如果为最高伤害值，替换
                            if (hope_dmg > max_dmg.get()) {
                                max_dmg.set(hope_dmg);
                                max_attack.set(attackLocalSet);
                                max_hp.set(hpLocalSet);
                                max_defend.set(defendLocalSet);
                                max_elementalMastery.set(elementalMasteryLocalSet);
                                max_energyRecharge.set(energyRechargeLocalSet);
                                max_critRate.set(critRateLocalSet);
                                max_critDmg.set(critDmgLocalSet);

                                max_fireBonus.set(fire);
                                max_waterBonus.set(water);
                                max_dendroBonus.set(dendro);
                                max_thunderBonus.set(thunder);
                                max_windBonus.set(wind);
                                max_iceBonus.set(ice);
                                max_rockBonus.set(rock);
                                max_physicalBonus.set(physical);

                                flower_max.set(flower);
                                feather_max.set(feather);
                                sand_max.set(sand);
                                cup_max.set(cup);
                                head_max.set(head);
                            }
                        });
                    });
                });
            });
        });
        Date end = new Date();

        ComputeArtifactVo result = new ComputeArtifactVo();
        result.setComputeParam(query.getComputeParam());
        result.setDamage(max_dmg.get());
        result.setAttack(max_attack.get());
        result.setHp(max_hp.get());
        result.setDefend(max_defend.get());
        result.setElementalMastery(max_elementalMastery.get());
        result.setEnergyRecharge(max_energyRecharge.get());
        result.setCriticalRate(max_critRate.get());
        result.setCriticalDamage(max_critDmg.get());

        result.setFireBonus(max_fireBonus.get());
        result.setWaterBonus(max_waterBonus.get());
        result.setDendroBonus(max_dendroBonus.get());
        result.setThunderBonus(max_thunderBonus.get());
        result.setWindBonus(max_windBonus.get());
        result.setIceBonus(max_iceBonus.get());
        result.setRockBonus(max_rockBonus.get());
        result.setPhysicalBonus(max_physicalBonus.get());

        result.setFlower(flower_max.get());
        result.setFeather(feather_max.get());
        result.setSand(sand_max.get());
        result.setCup(cup_max.get());
        result.setHead(head_max.get());
//        System.out.println("最大伤害值：" + max_dmg.get());
//        System.out.println("攻击力" + max_attack.get());
//        System.out.println("生命值" + max_hp.get());
//        System.out.println("防御力" + max_defend.get());
//        System.out.println("元素精通" + max_elementalMastery.get());
//        System.out.println("元素充能效率" + max_energyRecharge.get());
//        System.out.println("暴击率" + max_critRate.get());
//        System.out.println("暴击伤害" + max_critDmg.get());
//
//        System.out.println("火元素伤害加成" + max_fireBonus.get());
//        System.out.println("水元素伤害加成" + max_waterBonus.get());
//        System.out.println("草元素伤害加成" + max_dendroBonus.get());
//        System.out.println("雷元素伤害加成" + max_thunderBonus.get());
//        System.out.println("风元素伤害加成" + max_windBonus.get());
//        System.out.println("冰元素伤害加成" + max_iceBonus.get());
//        System.out.println("岩元素伤害加成" + max_rockBonus.get());
//        System.out.println("物理伤害加成" + max_physicalBonus.get());
//
//        printA(flower_max.get());
//        printA(feather_max.get());
//        printA(sand_max.get());
//        printA(cup_max.get());
//        printA(head_max.get());

        System.out.println("for循环时间： " + (end.getTime() - start.getTime()));

        return result;

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