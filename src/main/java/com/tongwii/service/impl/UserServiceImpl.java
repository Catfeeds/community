package com.tongwii.service.impl;

import com.tongwii.constant.UserConstants;
import com.tongwii.dao.BaseDao;
import com.tongwii.dao.UserDao;
import com.tongwii.po.FileEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IFileService;
import com.tongwii.service.IUserService;
import com.tongwii.util.Encoder.MD5PasswordEncoder;
import com.tongwii.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 用户Service
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
@Service(value = "userService")
public class UserServiceImpl extends BaseServiceImpl<UserEntity> implements IUserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IFileService fileService;

    private MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();

    @Override
    public BaseDao<UserEntity, String> getDao() {
        return userDao;
    }

    @Override
    public UserEntity findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    @Override
    public void save(UserEntity userEntity) {
        userEntity.setPassword(md5PasswordEncoder.encoder(userEntity.getPassword()));
        userEntity.setAddTime(new Date());
        userEntity.setState(UserConstants.USER_STATUS_ACTIVE);
        userDao.save(userEntity);
    }

    @Override
    public String updateUserAvatorById(String userId, MultipartFile file) throws IOException {
        String id = UUID.randomUUID().toString();
        String suffix = FileUtil.getFileSuffix(file.getOriginalFilename());
        String relativeUrl = FileUtil.uploadFile(file, id + suffix);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(FileUtil.rtnFileType(file.getOriginalFilename()));
        fileEntity.setFilePath(relativeUrl);
        fileEntity.setUploadUserId(userId);
        fileService.save(fileEntity);
        userDao.updateAvatorById(userId, fileEntity.getId());
        return relativeUrl;
    }
}
