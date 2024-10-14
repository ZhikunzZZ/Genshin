package com.gstool.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PcPspCockpitMvlineVO {

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