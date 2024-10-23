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

    public void calculateMultipliers(WeaponDTO weapon, CharacterDTO character,
                                                              AttributeAndMultiplierZoneDTO result, Integer normalAttackLevel,
                                                              Integer elementalSkillLevel, Integer elementalBurstLevel,
                                                              Integer constellation, String target) {

        double baseAttack = character.getAttack() + weapon.getBaseAttack();
        double baseDefend = character.getDefend();
        double baseLife = character.getLife();

        if(target.equals("arlecchinoNormalAttack")){

            double rd = baseGetMethod.getNormalAttackMultiplierByIdAndLevel("Arlecchino_rd", normalAttackLevel);
            double hit1 = baseGetMethod.getNormalAttackMultiplierByIdAndLevel("Arlecchino_1", normalAttackLevel);

            result.setBaseDamageMultiplierZone(result.getBaseDamageMultiplierZone() + (rd * 1.7 + hit1));
            result.setPyroDamageBonus(result.getPyroDamageBonus() + 0.4); //0.4是生活天赋火伤（后续应该修改）

            //命座加成
            if(constellation == 6){
                result.setCriticalRate(result.getCriticalRate() + 0.1);
                result.setCriticalDamage(result.getCriticalDamage() + 0.7);
            }

            if(constellation >= 1){
                result.setBaseDamageMultiplierZone(result.getBaseDamageMultiplierZone() + 1.7);
            }


        }
    }
}
