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
@TableName("RECOMMEND_ARTIFACT")
public class RecommendArtifactDTO {

        private String cup2;

        private String sand3;

        private String cup1;

        private String sand1;

        private String head4;

        private String cup4;

        private String head2;

        @TableId(type = IdType.ASSIGN_UUID)
        private String id;

        private String cup3;

        private String head3;

        private String head1;

        private String sand2;

}