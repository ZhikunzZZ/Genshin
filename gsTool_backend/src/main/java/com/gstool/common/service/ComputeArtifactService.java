package com.gstool.common.service;

import com.gstool.common.model.query.ComputeArtifactQuery;
import com.gstool.common.model.vo.ComputeArtifactVo;
import org.springframework.stereotype.Service;

@Service
public interface ComputeArtifactService {

    ComputeArtifactVo computeArtifact(ComputeArtifactQuery query);

}
