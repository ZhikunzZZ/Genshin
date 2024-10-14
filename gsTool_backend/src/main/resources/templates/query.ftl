package com.gstool.common.model.query;

import com.gstool.common.model.base.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${className}Query extends Query {

<#list columns as column>
    private ${column.javaType} ${column.camelCaseFieldName};

</#list>
}