package com.gstool.common.service;

import com.gstool.common.dao.ArtifactDao;
import com.gstool.common.model.base.ArtifactJson;
import com.gstool.common.model.base.ArtifactJsonItem;
import com.gstool.common.model.entity.ArtifactDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArtifactService {

    private ArtifactDao artifactDao;

    // 保存 Artifact 数据
    @Transactional
    public void saveArtifactsFromJson(String jsonContent, String userId) throws Exception {
        // 删除该用户的所有圣遗物
        artifactDao.deleteArtifactsByUserId(userId);

        // 使用 ObjectMapper 将 JSON 转为 ArtifactJson 对象
        ObjectMapper objectMapper = new ObjectMapper();
        ArtifactJson artifactJson = objectMapper.readValue(jsonContent, ArtifactJson.class);

        List<ArtifactDTO> artifacts = new ArrayList<>();
        // 处理 flower, feather, sand, cup, head 部位
        artifacts.addAll(convertToArtifacts(artifactJson.getFlower(), "flower", userId));
        artifacts.addAll(convertToArtifacts(artifactJson.getFeather(), "feather", userId));
        artifacts.addAll(convertToArtifacts(artifactJson.getSand(), "sand", userId));
        artifacts.addAll(convertToArtifacts(artifactJson.getCup(), "cup", userId));
        artifacts.addAll(convertToArtifacts(artifactJson.getHead(), "head", userId));

        // 存入数据库
        for (ArtifactDTO artifact : artifacts) {
            artifactDao.insert(artifact);
        }
    }

    // 将 ArtifactJson 的圣遗物数据转为数据库格式，并为每个圣遗物生成唯一ID
    private List<ArtifactDTO> convertToArtifacts(List<ArtifactJsonItem> jsonItems, String position, String userId) {
        List<ArtifactDTO> artifacts = new ArrayList<>();
        int count = 1;  // 用于生成递增的ID
        for (ArtifactJsonItem item : jsonItems) {
            ArtifactDTO artifact = new ArtifactDTO();
            String id = userId + "_" + position+ "_" + count++;
            System.out.println(id);
            artifact.setId(id);  // 生成的ID格式：userId-递增数字
            artifact.setSetName(item.getSetName());
            artifact.setPosition(position);  // flower, feather, etc.
            artifact.setMainTagName(item.getMainTag().getName());
            artifact.setMainTagValue(item.getMainTag().getValue());
            artifact.setLevel(item.getLevel());
            artifact.setStar(item.getStar());
            artifact.setUserId(userId);  // 存储用户ID

            // 处理 normalTags 副词条
            List<ArtifactJsonItem.NormalTag> normalTags = item.getNormalTags();
            if (!normalTags.isEmpty()) {
                artifact.setFirstNormalTagName(normalTags.get(0).getName());
                artifact.setFirstNormalTagValue(normalTags.get(0).getValue());
            }
            if (normalTags.size() > 1) {
                artifact.setSecondNormalTagName(normalTags.get(1).getName());
                artifact.setSecondNormalTagValue(normalTags.get(1).getValue());
            }
            if (normalTags.size() > 2) {
                artifact.setThirdNormalTagName(normalTags.get(2).getName());
                artifact.setThirdNormalTagValue(normalTags.get(2).getValue());
            }
            if (normalTags.size() > 3) {
                artifact.setForthNormalTagName(normalTags.get(3).getName());
                artifact.setForthNormalTagValue(normalTags.get(3).getValue());
            }

            artifacts.add(artifact);
        }
        return artifacts;
    }
}