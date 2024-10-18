package com.gstool.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

public class CrudCodeGenerator {


    public static void main(String[] args) {
        String url = "jdbc:dm://localhost:5236/DMSERVER"; //数据库
        String username = "GS_TOOL_1";
        String password = "123456789";
        String schemaName = "GS_TOOL_1"; //模式名
        String tableName = "ARTIFACT"; //表名
        String optionName = null; //DIY类名

        //项目根目录
        String homePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\gstool\\common\\";
        System.out.println(homePath);

        //输出文件地址
        String templatePath = "D:\\gs_tool\\Genshin\\gsTool_backend\\src\\main\\resources\\templates";
        String dtoPath = homePath + "model\\entity\\";
        String voPath = homePath + "model\\vo\\";
        String queryPath = homePath + "model\\query\\";
        String daoPath = homePath + "dao\\";
        String mapperPath = "D:\\tool_workspace\\gsTool_backend\\src\\main\\resources\\mapper\\";
        String servicePath = homePath + "service\\";
        String serviceImplPath = servicePath + "impl\\";
        String controllerPath = homePath + "controller\\";

        String tableComment = null;
        List<Map<String, String>> columnList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement stmt = connection.createStatement()) {

            ResultSet tableCommentResult = stmt.executeQuery(
                "SELECT comments FROM all_tab_comments WHERE table_name = '" + tableName.toUpperCase() + "' AND owner = '" + schemaName.toUpperCase() + "'"
            );

            if (tableCommentResult.next()) {
                if(tableCommentResult.getString("comments") == null) {
                    tableComment = "";
                }else{
                    tableComment = tableCommentResult.getString("comments");
                }
            }

            String sql = "SELECT " +
                    "    t1.column_name, " +
                    "    t1.data_type, " +
                    "    t1.data_length, " +
                    "    t1.data_scale, " +
                    "    t2.constraint_type, " +
                    "    col.comments AS column_comment " +
                    "FROM " +
                    "    all_tab_columns t1 " +
                    "LEFT JOIN " +
                    "    (SELECT " +
                    "         t1.column_name, " +
                    "         t1.table_name, " +
                    "         t2.constraint_type " +
                    "     FROM " +
                    "         all_ind_columns t1 " +
                    "     LEFT JOIN " +
                    "         all_constraints t2 " +
                    "     ON " +
                    "         t1.index_name = t2.index_name) t2 " +
                    "ON " +
                    "    t1.table_name = t2.table_name " +
                    "    AND t1.column_name = t2.column_name " +
                    "LEFT JOIN " +
                    "    all_col_comments col " +
                    "ON " +
                    "    t1.table_name = col.table_name " +
                    "    AND t1.column_name = col.column_name " +
                    "    AND t1.owner = col.owner " +
                    "WHERE " +
                    "    t1.table_name = '" + tableName + "' " +
                    "    AND t1.owner = '" + schemaName + "';";

            ResultSet columns = stmt.executeQuery(sql);

            while (columns.next()) {
                Map<String, String> columnInfo = new HashMap<>();
                columnInfo.put("columnName", columns.getString("column_name") != null ? columns.getString("column_name") : "");
                columnInfo.put("dataType", columns.getString("data_type") != null ? columns.getString("data_type") : "");
                columnInfo.put("dataLength", columns.getString("data_length") != null ? columns.getString("data_length") : "");
                columnInfo.put("dataScale", columns.getString("data_scale") != null ? columns.getString("data_scale") : "");
                columnInfo.put("constraintType", columns.getString("constraint_type") != null ? columns.getString("constraint_type") : "");
                columnInfo.put("columnComment", columns.getString("column_comment") != null ? columns.getString("column_comment") : "");
                columnList.add(columnInfo);
            }

            generate(optionName, tableName, tableComment, columnList, dtoPath, voPath, queryPath, daoPath, mapperPath, servicePath, serviceImplPath, controllerPath, templatePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void generate(String optionName, String tableName, String tableComment, List<Map<String, String>> columns, String dtoPath, String voPath,
                                 String queryPath, String daoPath, String mapperPath, String servicePath, String serviceImplPath, String controllerPath,
                                 String templatePath) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        try {
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            cfg.setDefaultEncoding("UTF-8");

            Map<String, Object> templateData = new HashMap<>();

            String className = toCamelCase(tableName, 1);
            String classNameCamelCase = toCamelCase(tableName, 0);
            if (optionName != null) {
                className = optionName;
                classNameCamelCase = Character.toLowerCase(optionName.charAt(0)) + optionName.substring(1);
            }

            templateData.put("tableName", tableName);
            templateData.put("classNameCamelCase", classNameCamelCase);
            templateData.put("className", className);
            templateData.put("tableComment", tableComment);
            templateData.put("columns", columns);

            for (Map<String, String> column : columns) {
                column.put("fieldName",column.get("columnName"));
                column.put("camelCaseFieldName", toCamelCase(column.get("columnName"), 0));
                column.put("javaType", mapDataType(column.get("dataType"), column.get("dataScale")));
            }
            templateData.put("columns", columns);

            generateFile(cfg, "dto.ftl", templateData, dtoPath + className + "DTO.java");
//            generateFile(cfg, "vo.ftl", templateData, voPath + className + "VO.java");
//            generateFile(cfg, "query.ftl", templateData, queryPath + className + "Query.java");
//            generateFile(cfg, "dao.ftl", templateData, daoPath + className + "Dao.java");
//            generateFile(cfg, "service.ftl", templateData, servicePath + className + "Service.java");
//            generateFile(cfg, "serviceImpl.ftl", templateData, serviceImplPath + className + "ServiceImpl.java");
//            generateFile(cfg, "controller.ftl", templateData, controllerPath + className + "Controller.java");
//            generateFile(cfg, "mapper.ftl", templateData, mapperPath + className + "Mapper.xml");

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }


    private static void generateFile(Configuration cfg, String templateName, Map<String, Object> templateData, String outputPath) throws IOException, TemplateException {
        Template template = cfg.getTemplate(templateName);
        try (Writer writer = new BufferedWriter(new FileWriter(outputPath))) {
            template.process(templateData, writer);
        }
        System.out.println("File created: " + outputPath);
    }


    private static String mapDataType(String dbType, String scale) {
        switch (dbType) {
            case "VARCHAR2":
                return "String";
            case "NUMBER":
                return Integer.parseInt(scale) > 0 ? "Double" : "Integer";
            case "TIMESTAMP":
                return "Timestamp";
            default:
                return "String";
        }
    }


    private static String toCamelCase(String columnName, int option) {
        String[] parts = columnName.split("_");
        StringBuilder camelCase = new StringBuilder();

        if (option == 0) {
            camelCase.append(parts[0].toLowerCase());
        } else {
            camelCase.append(parts[0].substring(0, 1).toUpperCase()).append(parts[0].substring(1).toLowerCase());
        }

        for (int i = 1; i < parts.length; i++) {
            camelCase.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }
        return camelCase.toString();
    }
}