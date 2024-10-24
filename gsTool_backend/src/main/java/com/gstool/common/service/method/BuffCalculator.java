package com.gstool.common.service.method;

import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BuffCalculator {

    public void calculate(List<String> buffs, AttributeAndMultiplierZoneDTO a, double baseAttack, double baseHp, double baseDefend){
        for(String buff : buffs){

            switch(buff){
                case "钟离_玉璋护盾":
                    a.setResistance(a.getResistance() - 0.2);
                    break;
                case "班尼特_美妙旅程": //865为班尼特理论最大基础攻击力
                    double attackBonus = 865 *  (1.19 + 0.2);
                    a.setAttack(a.getAttack() + attackBonus);
                    break;
                case "班尼特_烈火与勇气":
                    a.setPyroDamageBonus(a.getPyroDamageBonus() + 0.15);
                    break;
            }
        }

    }
}
