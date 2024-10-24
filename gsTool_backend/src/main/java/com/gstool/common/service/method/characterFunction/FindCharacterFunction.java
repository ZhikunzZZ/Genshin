package com.gstool.common.service.method.characterFunction;

import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.entity.CharacterDTO;
import com.gstool.common.model.entity.WeaponDTO;
import com.gstool.common.service.method.characterFunction.fire.ArlecchinoFunction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindCharacterFunction {

    private final ArlecchinoFunction arlecchinoFunction;

    public void characterFunction(WeaponDTO weapon, CharacterDTO character,
                                  AttributeAndMultiplierZoneDTO result, Integer normalAttackLevel,
                                  Integer elementalSkillLevel, Integer elementalBurstLevel,
                                  Integer constellation, String target) {

        switch(character.getName()){
            case "阿蕾奇诺":
                arlecchinoFunction.calculateMultipliers(weapon, character, result, normalAttackLevel,
                        elementalSkillLevel, elementalBurstLevel, constellation, target);
                break;


            default:
                break;
        }
    }
}
