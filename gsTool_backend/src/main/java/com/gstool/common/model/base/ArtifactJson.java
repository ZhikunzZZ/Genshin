package com.gstool.common.model.base;

import lombok.Data;

import java.util.List;

@Data
public class ArtifactJson {
    private String version;
    private List<ArtifactJsonItem> flower;
    private List<ArtifactJsonItem> feather;
    private List<ArtifactJsonItem> sand;
    private List<ArtifactJsonItem> cup;
    private List<ArtifactJsonItem> head;

}