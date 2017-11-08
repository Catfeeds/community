package com.tongwii.service;

import com.tongwii.dao.IFileDao;
import com.tongwii.domain.FileEntity;
import com.tongwii.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * fileService实现类
 *
 * @author: Zeral
 * @date: 2017/7/12
 */
@Service
@Transactional
public class FileService {
    private final IFileDao filedao;

    public FileService(IFileDao filedao) {
        this.filedao = filedao;
    }

    public FileEntity saveAndUploadFile(String userId, MultipartFile file) {
        FileEntity fileEntity = null;
        try {
            String id = UUID.randomUUID().toString();
            String suffix = FileUtil.getFileSuffix(file.getOriginalFilename());
            String relativeUrl = FileUtil.uploadFile(file, id + suffix);
            fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(FileUtil.rtnFileType(file.getOriginalFilename()));
            fileEntity.setFilePath(relativeUrl);
            fileEntity.setUploadUserId(userId);
            filedao.saveAndFlush(fileEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileEntity;
    }
}
