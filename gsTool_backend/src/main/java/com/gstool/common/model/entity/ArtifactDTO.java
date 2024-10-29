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

    private String position;

    private String mainTagName;

    private Double mainTagValue;

    private String firstNormalTagName;

    private Double firstNormalTagValue;

    private String secondNormalTagName;

    private Double secondNormalTagValue;

    private String thirdNormalTagName;

    private Double thirdNormalTagValue;

    private Double forthNormalTagValue;

    private String forthNormalTagName;

    private String setName;

    private Integer star;

    private Integer level;

    private String userId;

}