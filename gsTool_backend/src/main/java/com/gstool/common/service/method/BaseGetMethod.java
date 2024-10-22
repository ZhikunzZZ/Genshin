package com.gstool.common.service.method;

import com.gstool.common.dao.ArtifactDao;
import com.gstool.common.dao.NormalAttackMultiplierDao;
import com.gstool.common.dao.RecommendArtifactDao;
import com.gstool.common.model.base.ArtifactListDTO;
import com.gstool.common.model.entity.ArtifactDTO;
import com.gstool.common.model.entity.RecommendArtifactDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BaseGetMethod {

    private RecommendArtifactDao recommendArtifactDao;
    private ArtifactDao artifactDao;
    private NormalAttackMultiplierDao normalAttackMultiplierDao;

    public ArtifactListDTO getTargetArtifactList(String characterId, String userId){

        RecommendArtifactDTO dto = recommendArtifactDao.findById(characterId);

        List<ArtifactDTO> flowerList = artifactDao.findByPositionAndMainTag(userId, "flower", "lifeStatic",
                                                                    null, null, null); //条件判断
        List<ArtifactDTO> featherList = artifactDao.findByPositionAndMainTag(userId, "feather", "attackStatic",
                                                                    null, null, null);
        List<ArtifactDTO> sandList = artifactDao.findByPositionAndMainTag(userId, "sand", dto.getSand1(), dto.getSand2(),
                                                                            dto.getSand3(), null);
        List<ArtifactDTO> cupList = artifactDao.findByPositionAndMainTag(userId, "cup", dto.getCup1(), dto.getCup2(),
                                                                            dto.getCup3(), dto.getCup4());
        List<ArtifactDTO> headList = artifactDao.findByPositionAndMainTag(userId, "head", dto.getHead1(), dto.getHead2(),
                                                                            dto.getHead3(), dto.getHead4());

        ArtifactListDTO result = new ArtifactListDTO();
        result.setFlowerList(flowerList);
        result.setFeatherList(featherList);
        result.setSandList(sandList);
        result.setCupList(cupList);
        result.setHeadList(headList);

        return result;

    }



    public Double getNormalAttackMultiplierByIdAndLevel(String id, Integer level){

        return switch (level) {
            case 13 -> normalAttackMultiplierDao.findByName(id).getLv13();
            case 12 -> normalAttackMultiplierDao.findByName(id).getLv12();
            case 11 -> normalAttackMultiplierDao.findByName(id).getLv11();
            case 10 -> normalAttackMultiplierDao.findByName(id).getLv10();
            case 9 -> normalAttackMultiplierDao.findByName(id).getLv9();
            case 8 -> normalAttackMultiplierDao.findByName(id).getLv8();
            case 7 -> normalAttackMultiplierDao.findByName(id).getLv7();
            case 6 -> normalAttackMultiplierDao.findByName(id).getLv6();
            case 5 -> normalAttackMultiplierDao.findByName(id).getLv5();
            case 4 -> normalAttackMultiplierDao.findByName(id).getLv4();
            case 3 -> normalAttackMultiplierDao.findByName(id).getLv3();
            case 2 -> normalAttackMultiplierDao.findByName(id).getLv2();
            case 1 -> normalAttackMultiplierDao.findByName(id).getLv1();
            default -> 0.0;
        };
    }

}
