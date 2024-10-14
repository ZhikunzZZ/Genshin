package com.gstool.common.model.query;

import com.gstool.common.model.base.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuery extends Query {

    private String id;

    private String name;

}