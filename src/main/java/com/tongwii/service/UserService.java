package com.tongwii.service;

import com.tongwii.constant.UserConstants;
import com.tongwii.dao.IUserDao;
import com.tongwii.domain.FileEntity;
import com.tongwii.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 用户Service
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private FileService fileService;

    public UserEntity findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    public void save(UserEntity userEntity) {
        userEntity.setAddTime(new Date());
        userEntity.setState(UserConstants.USER_ENABLE);
        userDao.save(userEntity);
    }

    public String updateUserAvatorById(String userId, MultipartFile file) {
        FileEntity fileEntity = fileService.saveAndUploadFile(userId, file);
        userDao.updateAvatorById(userId, fileEntity.getId());
        return fileEntity.getFilePath();
    }

    public UserEntity findById(String createUserId) {
        return userDao.findOne(createUserId);
    }

    public void update(UserEntity userEntity) {
    }

    public List<UserEntity> findAll() {
        return userDao.findAll();
    }
}
