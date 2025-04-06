package com.xz.schoolnavinfo.controller;

import com.xz.schoolnavinfo.common.data.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    // 允许的图片 MIME 类型
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
        "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    // 允许的文件后缀
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");

    // 设定存储目录
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 1️⃣ 限制只能上传图片
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("文件为空");
        }

        try {
            // 获取文件 MIME 类型
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
                return Result.fail("只允许上传图片 (JPEG, PNG, GIF, WEBP)");
            }

            // 获取文件后缀名并检查
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return Result.fail("无法获取文件名");
            }

            String fileExtension = getFileExtension(originalFilename);
            if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
                return Result.fail("文件格式不支持");
            }

            // 生成唯一文件名
            String fileName = UUID.randomUUID() + "." + fileExtension;
            File destinationFile = new File(uploadDir + fileName);

            // 确保目录存在
            if (!destinationFile.getParentFile().exists()) {
                destinationFile.getParentFile().mkdirs();
            }

            file.transferTo(destinationFile);
            return Result.data(fileName, "文件上传成功");
        } catch (IOException e) {
            return Result.fail(e.getMessage());
        }
    }


    // 3️⃣ 获取文件扩展名
    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        return (lastIndex == -1) ? "" : filename.substring(lastIndex + 1);
    }
}
