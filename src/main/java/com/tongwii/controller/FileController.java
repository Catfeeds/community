package com.tongwii.controller;

import com.tongwii.domain.File;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/7/28.
 */
@RestController
@RequestMapping("/upload")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     *  添加图片文件接口
     *
     * */
    @PostMapping("/addPicture")
    public ResponseEntity addPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response){
        try {
            System.out.println("=========开始上传图片======================================");
            String id = SecurityUtils.getCurrentUserLogin();
            // 上传文件并更新用户地址
            File fileEntity = fileService.saveAndUploadFile(id, file);
            System.out.println("==========图片上传完毕======================================");
            // 使用了上传文件的输出流和response的返回json会出错，重置response
            response.reset();
            return ResponseEntity.ok(fileEntity.getId());
        } catch (Exception e) {
            response.reset();
            return ResponseEntity.badRequest().body("图片上传失败!");
        }
    }

    @PostMapping("/file")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile uploadFile) {

        if (uploadFile.isEmpty()) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        fileService.saveAndUploadFileToFTP(userId, uploadFile);

        return ResponseEntity.ok("上传成功 - " + uploadFile.getOriginalFilename());

    }

    @PostMapping("/files")
    public ResponseEntity uploadFileMulti(@RequestParam("files") MultipartFile[] uploadFiles) {

        String uploadedFileName = Arrays.stream(uploadFiles).map(MultipartFile::getOriginalFilename)
            .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        fileService.saveAndUploadFilesToFTP(userId, Arrays.asList(uploadFiles));
        return ResponseEntity.ok("上传成功 - " + uploadedFileName);
    }
}
