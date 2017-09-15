package com.tongwii.service.impl;

import com.tongwii.constant.UserConstants;
import com.tongwii.dao.BaseDao;
import com.tongwii.dao.UserDao;
import com.tongwii.po.FileEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IFileService;
import com.tongwii.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

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
        userEntity.setPassword(UserEntity.hashPassword(userEntity.getPassword()));
        userEntity.setAddTime(new Date());
        userEntity.setState(UserConstants.USER_ENABLE.byteValue());
        userDao.save(userEntity);
    }

    @Override
    public String updateUserAvatorById(String userId, MultipartFile file) throws IOException {
        FileEntity fileEntity = fileService.saveAndUploadFile(userId, file);
        userDao.updateAvatorById(userId, fileEntity.getId());
        return fileEntity.getFilePath();
    }
}
