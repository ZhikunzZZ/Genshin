package com.gstool.common.service.method;

import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.entity.WeaponDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BaseSetMethod {

    public void AddStatAndPassiveEffect(AttributeAndMultiplierZoneDTO dto, WeaponDTO weapon){
        //副词条
        switch (weapon.getSecondaryStatName()){
            case "CRITICAL_RATE":
                dto.setCriticalRate(dto.getCriticalRate() + weapon.getSecondaryStatValue());
                break;
            case "CRITICAL_DAMAGE":
                dto.setCriticalDamage(dto.getCriticalDamage() + weapon.getSecondaryStatValue());
                break;
        }
        //武器被动
        if(weapon.getPassiveEffect1() != null){
            setPassiveEffect(dto, weapon.getPassiveEffect1(), weapon.getPassiveEffectValue1());
        }
        if(weapon.getPassiveEffect2() != null){
            setPassiveEffect(dto, weapon.getPassiveEffect2(), weapon.getPassiveEffectValue2());
        }
        if(weapon.getPassiveEffect3() != null){
            setPassiveEffect(dto, weapon.getPassiveEffect3(), weapon.getPassiveEffectValue3());
        }

    }

    private void setPassiveEffect(AttributeAndMultiplierZoneDTO dto, String name, Double value){
        switch (name){
            case "dmg":
                dto.setBonusDamageMultiplierZone(dto.getBonusDamageMultiplierZone() + value);
        }
    }
}
