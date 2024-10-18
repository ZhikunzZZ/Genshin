package com.gstool.common.service.impl;

import com.gstool.common.dao.ArtifactDao;
import com.gstool.common.dao.CharacterDao;
import com.gstool.common.dao.NormalAttackMultiplierDao;
import com.gstool.common.dao.WeaponDao;
import com.gstool.common.model.entity.ArtifactDTO;
import com.gstool.common.model.entity.CharacterDTO;
import com.gstool.common.model.entity.NormalAttackMultiplierDTO;
import com.gstool.common.model.entity.WeaponDTO;
import com.gstool.common.model.query.ComputeArtifactQuery;
import com.gstool.common.service.ComputeArtifactService;
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

    @Override
    public void computeArtifact(ComputeArtifactQuery query){

        CharacterDTO character = characterDao.findByName(query.getCharacter());
        WeaponDTO weapon = weaponDao.findByName(query.getWeapon());
        List<NormalAttackMultiplierDTO> n = normalAttackMultiplierDao.findByName(character.getId());
        Double result = 1.137;
//        for (NormalAttackMultiplierDTO dto : n) {
//            if ("Arlecchino_1".equals(dto.getId())) {
//                result = dto.getLv13() * 0.01;
//                break;
//            }
//        }

        System.out.println(result);
        

        System.out.println(character.getName());
        System.out.println(weapon.getName());
        for (NormalAttackMultiplierDTO dto : n) {
            System.out.println(dto.getId());
        }

        List<ArtifactDTO> flowerList = artifactDao.findByPositionAndMainTag("111", "flower", "lifeStatic");
        List<ArtifactDTO> featherList = artifactDao.findByPositionAndMainTag("111", "feather", "attackStatic");
        List<ArtifactDTO> sandList = artifactDao.findByPositionAndMainTag("111", "sand", "attackPercentage");
        List<ArtifactDTO> cupList = artifactDao.findByPositionAndMainTag("111", "cup", "fireBonus");
        List<ArtifactDTO> headList = artifactDao.findByPositionAndMainTag("111", "head", "critical");

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


                            double baseAttack = character.getAttack() + weapon.getBaseAttack();
                            double attack = baseAttack;
                            double critRate = character.getCriticalRate() * 0.01 + weapon.getSecondaryStatValue() * 0.01;
                            double critDmg = character.getCriticalDmg() * 0.01;
                            double fireBonus = 0.0;

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

                            double base_part = attack * (result + (2.884 * 1.7) + (1 * 1.7));
                            double crit_part = (1 + critDmg + 0.7) * (critRate + 0.1) + 1 * (1 - critRate - 0.1);
                            if(isMatching4){
                                hope_dmg = base_part * (1 + 0.36 + 0.54 + 0.4 + fireBonus) * crit_part * 0.505 * 0.9;

                            }else {
                                hope_dmg = base_part * (1 + 0.36 + 0.4 + fireBonus) * crit_part * 0.505 * 0.9;
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
