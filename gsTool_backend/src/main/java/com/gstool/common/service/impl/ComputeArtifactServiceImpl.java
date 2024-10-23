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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

        //private Double criticalMultiplierZone; 暴击乘区为最后根据暴击率与爆伤计算
        //基础伤害乘区
        a.setBaseDamageMultiplierZone(0.0);
        //加成乘区
        a.setBonusDamageMultiplierZone(1.0);
        //防御乘区（同等级默认为0.5）
        a.setDefenseMultiplierZone(0.5);
        //抗性乘区
        a.setResistanceMultiplierZone(0.9);

        //加入角色自身数值（天赋，命座，普攻或技能倍率）
        arlecchinoFunction.calculateMultipliers(weapon, character, a, query.getNormaLAttackLevel(),
                query.getElementalSkillLevel(), query.getElementalBurstLevel(),
                query.getConstellation(), query.getComputeParam());

        //加入武器副词条以及武器被动
        baseSetMethod.AddStatAndPassiveEffect(a, weapon);

        //筛选有效圣遗物
        ArtifactListDTO targetList = baseGetMethod.getTargetArtifactList(character.getId(), "111");

        List<ArtifactDTO> flowerList = targetList.getFlowerList();
        List<ArtifactDTO> featherList = targetList.getFeatherList();
        List<ArtifactDTO> sandList = targetList.getSandList();
        List<ArtifactDTO> cupList = targetList.getCupList();
        List<ArtifactDTO> headList = targetList.getHeadList();

        AtomicReference<Double> max_dmg = new AtomicReference<>(0.0);
        AtomicReference<Double> max_attack = new AtomicReference<>(0.0);
        AtomicReference<Double> max_hp = new AtomicReference<>(0.0);
        AtomicReference<Double> max_defense = new AtomicReference<>(0.0);
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
        double resistanceMultiplierZone = a.getResistanceMultiplierZone();

        flowerList.parallelStream().forEach(flower -> {
            featherList.parallelStream().forEach(feather -> {
                for (ArtifactDTO sand: sandList) {
                    for (ArtifactDTO cup : cupList) {
                        for (ArtifactDTO head : headList) {

                            double attack = a.getAttack();
                            double Hp = a.getHp();
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


                            attack += (getMainStatValue(sand, "attackPercentage") + getMainStatValue(cup, "attackPercentage")
                                    + getMainStatValue(head, "attackPercentage")
                                    + getSubStatValue(flower, "attackPercentage") + getSubStatValue(feather, "attackPercentage")
                                    + getSubStatValue(sand, "attackPercentage") + getSubStatValue(cup, "attackPercentage")
                                    + getSubStatValue(head, "attackPercentage")) * baseAttack;

                            attack += getMainStatValue(feather, "attackStatic")
                                    + getSubStatValue(flower, "attackStatic") + getSubStatValue(sand, "attackStatic")
                                    + getSubStatValue(cup, "attackStatic") + getSubStatValue(head, "attackStatic");

                            Hp += (getMainStatValue(sand, "lifePercentage") + getMainStatValue(cup, "lifePercentage")
                                    + getMainStatValue(head, "lifePercentage")
                                    + getSubStatValue(flower, "flowerPercentage") + getSubStatValue(feather, "lifePercentage")
                                    + getSubStatValue(sand, "lifePercentage") + getSubStatValue(cup, "lifePercentage")
                                    + getSubStatValue(head, "lifePercentage")) * Hp;

                            Hp += getMainStatValue(flower, "lifeStatic")
                                    + getSubStatValue(feather, "lifeStatic") + getSubStatValue(sand, "lifeStatic")
                                    + getSubStatValue(cup, "lifeStatic") + getSubStatValue(head, "lifeStatic");

                            defense += (getMainStatValue(sand, "defendPercentage") + getMainStatValue(cup, "defendPercentage")
                                    + getMainStatValue(head, "defendPercentage") + getSubStatValue(flower, "defendPercentage")
                                    + getSubStatValue(feather, "defendPercentage") + getSubStatValue(sand, "defendPercentage")
                                    + getSubStatValue(cup, "defendPercentage") + getSubStatValue(head, "defendPercentage")) * defense;

                            defense += getSubStatValue(feather, "defendStatic")
                                    + getSubStatValue(flower, "defendStatic") + getSubStatValue(sand, "defendStatic")
                                    + getSubStatValue(cup, "defendStatic") + getSubStatValue(head, "defendStatic");

                            critRate += getMainStatValue(head, "critical") + getSubStatValue(flower, "critical")
                                    + getSubStatValue(feather, "critical") + getSubStatValue(sand, "critical")
                                    + getSubStatValue(cup, "critical") + getSubStatValue(head, "critical");

                            critDmg += getMainStatValue(head, "criticalDamage") + getSubStatValue(flower, "criticalDamage")
                                    + getSubStatValue(feather, "criticalDamage") + getSubStatValue(sand, "criticalDamage")
                                    + getSubStatValue(cup, "criticalDamage") + getSubStatValue(head, "criticalDamage");

                            elementalMastery += getMainStatValue(sand, "elementalMastery") + getMainStatValue(cup, "elementalMastery")
                                    + getMainStatValue(head, "elementalMastery")
                                    + getSubStatValue(flower, "elementalMastery") + getSubStatValue(feather, "elementalMastery")
                                    + getSubStatValue(sand, "elementalMastery") + getSubStatValue(cup, "elementalMastery")
                                    + getSubStatValue(head, "elementalMastery");

                            energyRecharge += getMainStatValue(sand, "recharge")
                                    + getSubStatValue(flower, "recharge") + getSubStatValue(feather, "recharge")
                                    + getSubStatValue(sand, "recharge") + getSubStatValue(cup, "recharge")
                                    + getSubStatValue(head, "recharge");

                            fireBonus += getMainStatValue(cup, "fireBonus");
                            waterBonus += getMainStatValue(head, "waterBonus");
                            dendroBonus += getMainStatValue(head, "dendroBonus");
                            thunderBonus += getMainStatValue(head, "thunderBonus");
                            windBonus += getMainStatValue(head, "windBonus");
                            iceBonus += getMainStatValue(head, "iceBonus");
                            rockBonus += getMainStatValue(head, "rockBonus");
                            physicalBonus += getMainStatValue(head, "physicalBonus");


                            double hope_dmg = 0.0;

                            AttributeAndMultiplierZoneDTO b = new AttributeAndMultiplierZoneDTO();
                            b.setAttack(attack);
                            b.setHp(Hp);
                            b.setDefense(defense);
                            b.setElementalMastery(elementalMastery);
                            b.setEnergyRecharge(energyRecharge);
                            b.setCriticalRate(critRate);
                            b.setCriticalDamage(critDmg);

                            b.setPyroDamageBonus(fireBonus);            //火元素
                            b.setHydroDamageBonus(waterBonus);          //水元素
                            b.setDendroDamageBonus(dendroBonus);        //草元素
                            b.setElectroDamageBonus(thunderBonus);      //雷元素
                            b.setAnemoDamageBonus(windBonus);           //风元素
                            b.setCryoDamageBonus(iceBonus);             //冰元素
                            b.setGeoDamageBonus(rockBonus);             //岩元素
                            b.setPhysicalDamageBonus(physicalBonus);    //物理

                            b.setBaseDamageMultiplierZone(baseDamageMultiplierZone);
                            b.setBonusDamageMultiplierZone(bonusDamageMultiplierZone);
                            b.setDefenseMultiplierZone(defenseMultiplierZone);
                            b.setResistanceMultiplierZone(resistanceMultiplierZone);

                            //加入圣遗物套装加成
                            artifactSetBonus(flower, feather, sand, cup, head, baseAttack, b);

                            //计算暴击乘区
                            double crit_part = (1 + critDmg) * (critRate) + 1 * (1 - critRate);

                            //计算伤害
                            hope_dmg = (b.getBaseDamageMultiplierZone() * b.getAttack()) * (b.getBonusDamageMultiplierZone() + fireBonus)
                                    * crit_part * b.getDefenseMultiplierZone() * b.getResistanceMultiplierZone();

                            //如果为最高伤害值，替换
                            if (hope_dmg > max_dmg.get()) {
                                max_dmg.set(hope_dmg);
                                max_attack.set(b.getAttack());
                                max_hp.set(b.getHp());
                                max_defense.set(b.getDefense());
                                max_elementalMastery.set(b.getElementalMastery());
                                max_energyRecharge.set(b.getEnergyRecharge());
                                max_critRate.set(b.getCriticalRate());
                                max_critDmg.set(b.getCriticalDamage());

                                max_fireBonus.set(b.getPyroDamageBonus());
                                max_waterBonus.set(b.getHydroDamageBonus());
                                max_dendroBonus.set(b.getDendroDamageBonus());
                                max_thunderBonus.set(b.getElectroDamageBonus());
                                max_windBonus.set(b.getAnemoDamageBonus());
                                max_iceBonus.set(b.getCryoDamageBonus());
                                max_rockBonus.set(b.getGeoDamageBonus());
                                max_physicalBonus.set(b.getPhysicalDamageBonus());

                                flower_max.set(flower);
                                feather_max.set(feather);
                                sand_max.set(sand);
                                cup_max.set(cup);
                                head_max.set(head);
                            }

                        }
                    }
                }
            });
        });


        System.out.println("最大伤害值：" + max_dmg.get());
        System.out.println("攻击力" + max_attack.get());
        System.out.println("生命值" + max_hp.get());
        System.out.println("防御力" + max_defense.get());
        System.out.println("元素精通" + max_elementalMastery.get());
        System.out.println("元素充能效率" + max_energyRecharge.get());
        System.out.println("暴击率" + max_critRate.get());
        System.out.println("暴击伤害" + max_critDmg.get());

        System.out.println("火元素伤害加成" + max_fireBonus.get());
        System.out.println("水元素伤害加成" + max_waterBonus.get());
        System.out.println("草元素伤害加成" + max_dendroBonus.get());
        System.out.println("雷元素伤害加成" + max_thunderBonus.get());
        System.out.println("风元素伤害加成" + max_windBonus.get());
        System.out.println("冰元素伤害加成" + max_iceBonus.get());
        System.out.println("岩元素伤害加成" + max_rockBonus.get());
        System.out.println("物理伤害加成" + max_physicalBonus.get());

        printA(flower_max.get());
        printA(feather_max.get());
        printA(sand_max.get());
        printA(cup_max.get());
        printA(head_max.get());

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

    private void artifactSetBonus(ArtifactDTO flower, ArtifactDTO feather, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, Double baseAttack, AttributeAndMultiplierZoneDTO b) {
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
                        b.setBonusDamageMultiplierZone(b.getBonusDamageMultiplierZone() + 0.54);
                        break;

                    default:
                        break;
                }
            }

            if (count >= 2) {
                switch (setName) {
                    case "FragmentOfHarmonicWhimsy", "Gladiator's Finale", "Shimenawa's Reminiscence":
                        b.setAttack(b.getAttack() + 0.18 * baseAttack);
                        break;
                    case "Crimson Witch of Flames":
                        b.setBonusDamageMultiplierZone(b.getBonusDamageMultiplierZone() + 0.15); //要修改
                        break;

                    default:
                        break;
                }
            }
        }
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