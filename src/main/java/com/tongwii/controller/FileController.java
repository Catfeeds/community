package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.domain.FileEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.FileService;
import com.tongwii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2017/7/28.
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    private TongWIIResult result = new TongWIIResult();
    /**
     *  添加图片文件接口
     *
     * */
    @PostMapping(path = "/addPicture", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TongWIIResult addPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response){
        try {
            System.out.println("=========开始上传图片======================================");
            String account = SecurityUtils.getCurrentUserLogin();
            UserEntity userEntity = userService.findByAccount(account);
            // 上传文件并更新用户地址
            FileEntity fileEntity = fileService.saveAndUploadFile(userEntity.getId(), file);
            result.successResult("图片上传成功", fileEntity.getId());
            System.out.println("==========图片上传完毕======================================");
            // 使用了上传文件的输出流和response的返回json会出错，重置response
            response.reset();
            return result;
        } catch (Exception e) {
            result.errorResult("图片上传失败");
            response.reset();
            return result;
        }
    }
}
