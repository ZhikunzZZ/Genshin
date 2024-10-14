<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.gstool.common.dao.${className}Dao">

    <sql id="commonWhere">
        <#list columns as column>
        <#if column.dataType == "NUMBER" || column.dataType == "TIMESTAMP">
        <if test="query.${column.camelCaseFieldName} != null ">
        <#else>
        <if test="query.${column.camelCaseFieldName} != null and query.${column.camelCaseFieldName} != '' ">
        </#if>
            and t1.${column.fieldName} = #${"{"}query.${column.camelCaseFieldName}${"}"}
        </if>
        </#list>
    </sql>

    <select id="queryList" resultType="com.gstool.common.model.vo.${className}VO" parameterType="com.gstool.common.model.query.${className}Query">
        select
        <#list columns as column>
            t1.${column.fieldName}<#if column_index != columns?size - 1>,</#if>
        </#list>
        from ${tableName} t1
        <where>
            <include refid="commonWhere" />
        </where>
    </select>
</mapper>