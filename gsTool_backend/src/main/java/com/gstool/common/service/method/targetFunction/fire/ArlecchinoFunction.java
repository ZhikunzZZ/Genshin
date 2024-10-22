package com.gstool.common.service.method.targetFunction.fire;

import com.gstool.common.dao.NormalAttackMultiplierDao;
import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.base.AttributeDTO;
import com.gstool.common.model.entity.CharacterDTO;
import com.gstool.common.model.entity.WeaponDTO;
import com.gstool.common.service.method.BaseGetMethod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ArlecchinoFunction {

    final BaseGetMethod baseGetMethod;

    public AttributeAndMultiplierZoneDTO calculateMultipliers(WeaponDTO weapon, CharacterDTO character, Integer constellation,
                                     Integer normalAttackLevel, Integer elementalSkillLevel, Integer elementalBurstLevel,
                                     String target) {

        double baseAttack = character.getAttack() + weapon.getBaseAttack();
        double baseDefend = character.getDefend();
        double baseLife = character.getLife();
        double elementalMastery = character.getElementalMastery();
        double recharge = character.getRecharge();
        double critRate = 0.0;
        double critDmg = 0.0;
        double fireBonus = 0.0; //后续数据库还应添加相应元素加成字段

        double baseZone = 0.0;
        double damageMultiplierZone = 0.0;

        AttributeAndMultiplierZoneDTO result = new AttributeAndMultiplierZoneDTO();

        if(target.equals("arlecchinoNormalAttack")){

            double rd = baseGetMethod.getNormalAttackMultiplierByIdAndLevel("Arlecchino_rd", normalAttackLevel);
            double hit1 = baseGetMethod.getNormalAttackMultiplierByIdAndLevel("Arlecchino_1", normalAttackLevel);

            baseZone += rd * 1.7 + hit1;

            damageMultiplierZone += 0.4; //0.4是生活天赋火伤（后续应该修改）


        if(constellation == 6){
            critRate += 0.1;
            critDmg += 0.7;
        }


        if(constellation >= 1){
            baseZone += 1.7;
        }

        result.setBaseDamageMultiplierZone(baseZone);
        result.setBonusDamageMultiplierZone(damageMultiplierZone);
        result.setCriticalRate(critRate);
        result.setCriticalDamage(critDmg);

        }

    return result;










    }







}
