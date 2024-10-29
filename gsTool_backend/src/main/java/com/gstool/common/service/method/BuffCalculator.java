package com.gstool.common.service.method;

import com.gstool.common.model.base.AttributeAndMultiplierZoneDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BuffCalculator {

    public void calculate(List<String> buffs, AttributeAndMultiplierZoneDTO a, double baseAttack, double baseHp, double baseDefend, String element){
        for(String buff : buffs){

            switch(buff) {
                case "钟离_玉璋护盾":
                    a.setResistance(a.getResistance() - 0.2);
                    break;
                case "班尼特_美妙旅程": //865为班尼特理论最大基础攻击力
                    double attackBonus = 865 * (1.19 + 0.2);
                    a.setAttack(a.getAttack() + attackBonus);
                    break;
                case "班尼特_烈火与勇气":
                    a.setPyroDamageBonus(a.getPyroDamageBonus() + 0.15);
                    break;
                case "枫原万叶_风物之诗咏", "烬城勇者绘卷4": //万叶默认1000精通
                    switch (element) {
                        case "fire":
                            a.setPyroDamageBonus(a.getPyroDamageBonus() + 0.4);
                            break;
                        case "water":
                            a.setHydroDamageBonus(a.getHydroDamageBonus() + 0.4);
                            break;
                        case "thunder":
                            a.setElectroDamageBonus(a.getElectroDamageBonus() + 0.4);
                            break;
                        case "ice":
                            a.setCryoDamageBonus(a.getCryoDamageBonus() + 0.4);
                            break;
                        default:
                            break;
                    }
                    break;
                case "希诺宁_音火锻淬_lv10": //默认e技能为10级，后续可修改
                    a.setResistance(a.getResistance() - 0.36);
                    break;
                case "翠绿之影4":
                    a.setResistance(a.getResistance() - 0.4);
                    break;

            }
        }

    }
}
