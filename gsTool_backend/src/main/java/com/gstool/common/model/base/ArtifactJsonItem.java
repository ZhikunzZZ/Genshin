package com.gstool.common.model.base;

import lombok.Data;

import java.util.List;

@Data
public class ArtifactJsonItem {
    private String setName;
    private String position;
    private MainTag mainTag;
    private List<NormalTag> normalTags;
    private boolean omit;
    private int level;
    private int star;
    private String equip;

    // 内部类
    @Data
    public static class MainTag {
        private String name;
        private double value;

        // Getters and Setters
    }

    @Data
    public static class NormalTag {
        private String name;
        private double value;

        // Getters and Setters
    }

    // Getters and Setters
}