package com.gstool.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ARTIFACT")  // 对应数据库中的表名
public class ArtifactDTO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String setName;

    private String position;

    private String mainTagName;

    private Integer mainTagValue;

    private String firstNormalTagName;

    private Double firstNormalTagValue;

    private String secondNormalTagName;

    private Double secondNormalTagValue;

    private String thirdNormalTagName;

    private Double thirdNormalTagValue;

    private String forthNormalTagName;

    private Double forthNormalTagValue;

    private Integer level;

    private Integer star;

    private String userId;

}
