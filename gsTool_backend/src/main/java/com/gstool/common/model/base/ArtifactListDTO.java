package com.gstool.common.model.base;

import com.gstool.common.model.entity.ArtifactDTO;
import lombok.Data;

import java.util.List;

@Data
public class ArtifactListDTO {

    private List<ArtifactDTO> flowerList;

    private List<ArtifactDTO> featherList;

    private List<ArtifactDTO> sandList;

    private List<ArtifactDTO> cupList;

    private List<ArtifactDTO> headList;

}
