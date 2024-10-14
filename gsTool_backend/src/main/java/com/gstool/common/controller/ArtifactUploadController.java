package com.gstool.common.controller;

import com.gstool.common.model.base.ResultDTO;
import com.gstool.common.service.ArtifactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ArtifactUploadController {

    private final ArtifactService artifactService;

    @PostMapping("/upload-artifacts")
    public ResponseEntity<String> uploadArtifacts(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        try {
            // 将文件内容转换为字符串
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);

            // 通过服务层处理 JSON 文件并保存数据到数据库
            artifactService.saveArtifactsFromJson(content, userId);

            return ResponseEntity.ok("圣遗物数据上传成功！");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败：" + e.getMessage());
        }
    }
}