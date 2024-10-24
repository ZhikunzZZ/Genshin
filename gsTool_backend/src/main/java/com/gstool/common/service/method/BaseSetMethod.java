package com.gstool.common.service.method;

import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import com.gstool.common.model.entity.ArtifactDTO;
import com.gstool.common.model.entity.WeaponDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    public void artifactSetBonus(ArtifactDTO flower, ArtifactDTO feather, ArtifactDTO sand, ArtifactDTO cup, ArtifactDTO head, Double baseAttack, AttributeAndMultiplierZoneDTO b) {
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
}
