package com.tongwii.service.impl;

import com.tongwii.core.BaseDao;
import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.FileDao;
import com.tongwii.domain.FileEntity;
import com.tongwii.service.IFileService;
import com.tongwii.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class FileServiceImpl extends BaseServiceImpl<FileEntity> implements IFileService {
    @Autowired
    private FileDao filedao;

    @Override
    public BaseDao<FileEntity, String> getDao() {
        return filedao;
    }

    @Override
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
            save(fileEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileEntity;
    }
}
