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
@TableName("PC_PSP_COCKPIT_MVLINE")
public class PcPspCockpitMvlineDTO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

        private String creatorId;

        private Timestamp createTime;

        private String creator;

        private String updatorId;

        private Timestamp updateTime;

        private String updator;

        private String dataFrom;

        private String provinceCode;

        private String bureauCode;

        private Integer optimisticLockVersion;

        private String bureau;

        private String region;

        private String substationName;

        private String substationBusbarCode;

        private String feederName;

        private String voltageGrade;

        private Double totalLength;

        private Double cableTotalLength;

        private Double overheadLength;

        private Double lineTotalLength;

        private Integer circuitNum;

}