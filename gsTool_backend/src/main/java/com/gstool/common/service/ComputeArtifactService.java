package com.gstool.common.service;

import com.gstool.common.model.query.ComputeArtifactQuery;
import org.springframework.stereotype.Service;

@Service
public interface ComputeArtifactService {

    void computeArtifact(ComputeArtifactQuery query);

}
