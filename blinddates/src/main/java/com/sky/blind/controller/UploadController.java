package com.sky.blind.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @PostMapping
    public Result singleFileupload(MultipartFile file) {
        if (file.isEmpty()) {
            return new Result(false, StatusCode.ERROR, "文件为空");
        }
        try {
            String fileName = file.getOriginalFilename();
            File img = new File("/home/upload/" + System.currentTimeMillis() + fileName);
//            File img = new File("D:/upload/" + System.currentTimeMillis() + fileName);
//            fileName = "D:/upload/"+fileName;
            fileName = "/www/wwwroot/api.uixkei.cn/upload/"+fileName;
            file.transferTo(img);
            return new Result(true, StatusCode.OK, "上传成功",fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
