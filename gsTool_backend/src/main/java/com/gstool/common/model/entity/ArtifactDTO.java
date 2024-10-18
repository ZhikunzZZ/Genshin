package com.gstool.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ARTIFACT")
public class ArtifactDTO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Double secondNormalTagValue;

    private Integer star;

    private Double forthNormalTagValue;

    private String secondNormalTagName;

    private String position;

    private String firstNormalTagName;

    private String forthNormalTagName;

    private Integer level;

    private String userId;

    private Double mainTagValue;

    private Double firstNormalTagValue;

    private String thirdNormalTagName;

    private String setName;

    private String mainTagName;

    private Double thirdNormalTagValue;

}