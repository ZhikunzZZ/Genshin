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
@TableName("${tableName}")
public class ${className}DTO {

<#list columns as column>
    <#if column.constraintType == "P">@TableId(type = IdType.ASSIGN_UUID)
    </#if>
    private ${column.javaType} ${column.camelCaseFieldName};

</#list>
}