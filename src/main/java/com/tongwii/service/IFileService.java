package com.tongwii.service;

import com.tongwii.po.FileEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * fileServices接口
 *
 * @author: Zeral
 * @date: 2017 /7/12
 */
public interface IFileService extends IBaseService<FileEntity> {
    /**
     * Save and upload file file entity.
     *
     * @param userId the user id
     * @param file   the file
     * @return the file entity
     */
    FileEntity saveAndUploadFile(String userId, MultipartFile file);
}
