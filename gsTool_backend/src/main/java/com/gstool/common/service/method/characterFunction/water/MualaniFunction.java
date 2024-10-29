package com.gstool.common.service.method.characterFunction.water;

import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.entity.CharacterDTO;
import com.gstool.common.model.entity.WeaponDTO;
import com.gstool.common.service.method.BaseGetMethod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MualaniFunction {

    final BaseGetMethod baseGetMethod;

    public void calculateMultipliers(WeaponDTO weapon, CharacterDTO character,
                                     AttributeAndMultiplierZoneDTO result, Integer normalAttackLevel,
                                     Integer elementalSkillLevel, Integer elementalBurstLevel,
                                     Integer constellation, String target) {

//        double baseAttack = character.getAttack() + weapon.getBaseAttack();
//        double baseDefend = character.getDefend();
//        double baseLife = character.getLife();

        if(target.equals("mualaniElementalSkill")){

            double sb = baseGetMethod.getMultiplierByIdAndLevel("elementalSkill","Mualani_Sharky_Bite", elementalSkillLevel);
            double wm = baseGetMethod.getMultiplierByIdAndLevel("elementalSkill","Mualani_Wave_Momentum", elementalSkillLevel);
            double sba = baseGetMethod.getMultiplierByIdAndLevel("elementalSkill","Mualani_Surging_Bite_Additional", elementalSkillLevel);
            //技能倍率
            result.setBaseDamageMultiplierZone(result.getBaseDamageMultiplierZone() + (sb + wm*3 + sba));

            if(constellation >= 1){
                result.setBaseDamageMultiplierZone(result.getBaseDamageMultiplierZone() + 0.66);
            }


        }

    }
}
