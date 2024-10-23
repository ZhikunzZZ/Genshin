package com.gstool.common.service.method;
import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.entity.ArtifactDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
public class AttributeCalculator {

    public void calculate(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather, Double baseAttack, Double baseHp, Double baseDefend) {
        CompletableFuture<Void> calculationFuture = CompletableFuture.runAsync(() -> {
            calculateAttributes(b, sand, cup, head, flower, feather, baseAttack, baseHp, baseDefend);
        });

        calculationFuture.join(); // 阻塞等待计算完成
    }

    private void calculateAttributes(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather, Double baseAttack, Double baseHp, Double baseDefend) {
        calculateAttack(b, sand, cup, head, flower, feather, baseAttack);
        calculateHp(b, sand, cup, head, flower, feather, baseHp);
        calculateDefense(b, sand, cup, head, flower, feather, baseDefend);
        calculateCritical(b, sand, cup, head, flower, feather);
        calculateElementalMastery(b, sand, cup, head, flower, feather);
        calculateEnergyRecharge(b, sand, cup, head, flower, feather);
        calculateBonus(b, sand, cup, head, flower, feather);
    }

    private void calculateAttack(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather, Double baseAttack) {

        double attack_percentage = (getMainStatValue(sand, "attackPercentage") + getMainStatValue(cup, "attackPercentage")
                + getMainStatValue(head, "attackPercentage")
                + getSubStatValue(flower, "attackPercentage") + getSubStatValue(feather, "attackPercentage")
                + getSubStatValue(sand, "attackPercentage") + getSubStatValue(cup, "attackPercentage")
                + getSubStatValue(head, "attackPercentage")) * baseAttack;

        double attack = getMainStatValue(feather, "attackStatic")
                + getSubStatValue(flower, "attackStatic") + getSubStatValue(sand, "attackStatic")
                + getSubStatValue(cup, "attackStatic") + getSubStatValue(head, "attackStatic");

        b.setAttack(b.getAttack() + attack_percentage + attack);

    }

    private void calculateHp(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather, Double baseHp) {

        double hp_percentage = (getMainStatValue(sand, "lifePercentage") + getMainStatValue(cup, "lifePercentage")
                + getMainStatValue(head, "lifePercentage")
                + getSubStatValue(flower, "flowerPercentage") + getSubStatValue(feather, "lifePercentage")
                + getSubStatValue(sand, "lifePercentage") + getSubStatValue(cup, "lifePercentage")
                + getSubStatValue(head, "lifePercentage")) * baseHp;

        double hp = getMainStatValue(flower, "lifeStatic")
                + getSubStatValue(feather, "lifeStatic") + getSubStatValue(sand, "lifeStatic")
                + getSubStatValue(cup, "lifeStatic") + getSubStatValue(head, "lifeStatic");

        b.setHp(b.getHp() + hp_percentage + hp);

    }

    private void calculateDefense(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather, Double baseDefend) {

        double defense_percentage = (getMainStatValue(sand, "defendPercentage") + getMainStatValue(cup, "defendPercentage")
                + getMainStatValue(head, "defendPercentage") + getSubStatValue(flower, "defendPercentage")
                + getSubStatValue(feather, "defendPercentage") + getSubStatValue(sand, "defendPercentage")
                + getSubStatValue(cup, "defendPercentage") + getSubStatValue(head, "defendPercentage")) * baseDefend;

        double defense = getSubStatValue(feather, "defendStatic")
                + getSubStatValue(flower, "defendStatic") + getSubStatValue(sand, "defendStatic")
                + getSubStatValue(cup, "defendStatic") + getSubStatValue(head, "defendStatic");

        b.setDefense(b.getDefense() + defense_percentage + defense);

    }

    private void calculateCritical(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather) {

        double critRate = getMainStatValue(head, "critical") + getSubStatValue(flower, "critical")
                + getSubStatValue(feather, "critical") + getSubStatValue(sand, "critical")
                + getSubStatValue(cup, "critical") + getSubStatValue(head, "critical");

        double critDmg = getMainStatValue(head, "criticalDamage") + getSubStatValue(flower, "criticalDamage")
                + getSubStatValue(feather, "criticalDamage") + getSubStatValue(sand, "criticalDamage")
                + getSubStatValue(cup, "criticalDamage") + getSubStatValue(head, "criticalDamage");

        b.setCriticalRate(b.getCriticalRate() + critRate);
        b.setCriticalDamage(b.getCriticalDamage() + critDmg);

    }

    private void calculateElementalMastery(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather) {

        double elementalMastery = getMainStatValue(sand, "elementalMastery") + getMainStatValue(cup, "elementalMastery")
                + getMainStatValue(head, "elementalMastery")
                + getSubStatValue(flower, "elementalMastery") + getSubStatValue(feather, "elementalMastery")
                + getSubStatValue(sand, "elementalMastery") + getSubStatValue(cup, "elementalMastery")
                + getSubStatValue(head, "elementalMastery");

        b.setElementalMastery(b.getElementalMastery() + elementalMastery);
    }

    private void calculateEnergyRecharge(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather) {

        double energyRecharge = getMainStatValue(sand, "recharge")
                + getSubStatValue(flower, "recharge") + getSubStatValue(feather, "recharge")
                + getSubStatValue(sand, "recharge") + getSubStatValue(cup, "recharge")
                + getSubStatValue(head, "recharge");

        b.setEnergyRecharge(b.getEnergyRecharge() + energyRecharge);

    }

    private void calculateBonus(AttributeAndMultiplierZoneDTO b, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, ArtifactDTO flower, ArtifactDTO feather) {

        b.setPyroDamageBonus(b.getPyroDamageBonus() + getMainStatValue(cup, "fireBonus"));            //火元素
        b.setHydroDamageBonus(b.getHydroDamageBonus() + getMainStatValue(head, "waterBonus"));          //水元素
        b.setDendroDamageBonus(b.getDendroDamageBonus() + getMainStatValue(head, "dendroBonus"));        //草元素
        b.setElectroDamageBonus(b.getElectroDamageBonus() + getMainStatValue(head, "thunderBonus"));      //雷元素
        b.setAnemoDamageBonus(b.getAnemoDamageBonus() + getMainStatValue(head, "windBonus"));           //风元素
        b.setCryoDamageBonus(b.getCryoDamageBonus() + getMainStatValue(head, "iceBonus"));             //冰元素
        b.setGeoDamageBonus(b.getGeoDamageBonus() + getMainStatValue(head, "rockBonus"));             //岩元素
        b.setPhysicalDamageBonus(b.getPhysicalDamageBonus() + getMainStatValue(head, "physicalBonus"));    //物理

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

