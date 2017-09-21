package com.tongwii.service.impl;

import com.tongwii.constant.UserConstants;
import com.tongwii.core.BaseDao;
import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.UserDao;
import com.tongwii.domain.FileEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.service.IFileService;
import com.tongwii.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 用户Service
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
@Service
@Transactional
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
        userEntity.setAddTime(new Date());
        userEntity.setState(UserConstants.USER_ENABLE);
        userDao.save(userEntity);
    }

    @Override
    public String updateUserAvatorById(String userId, MultipartFile file) {
        FileEntity fileEntity = fileService.saveAndUploadFile(userId, file);
        userDao.updateAvatorById(userId, fileEntity.getId());
        return fileEntity.getFilePath();
    }
}
