package com.tongwii.service;

import com.tongwii.constant.AuthoritiesConstants;
import com.tongwii.constant.UserConstants;
import com.tongwii.dao.IUserDao;
import com.tongwii.domain.FileEntity;
import com.tongwii.domain.RoleEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoleEntity;
import com.tongwii.dto.UserDto;
import com.tongwii.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    public UserDto save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setAddTime(new Date());
        userEntity.setState(UserConstants.USER_ENABLE);
        userDao.save(userEntity);
        RoleEntity roleEntity = roleService.findRoleByCode(AuthoritiesConstants.USER);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleEntity.getId());
        userRoleEntity.setUserId(userEntity.getId());
        userRoleService.save(userRoleEntity);
        return userMapper.userToUserDTO(userEntity);
    }

    public String updateUserAvatorById(String userId, MultipartFile file) {
        FileEntity fileEntity = fileService.saveAndUploadFileToFTP(userId, file);
        userDao.updateAvatorById(userId, fileEntity.getId());
        return fileEntity.getFilePath();
    }

    public UserEntity findById(String createUserId) {
        return userDao.findOne(createUserId);
    }

    public void update(UserEntity userEntity) {
        userDao.save(userEntity);
    }

    public List<UserEntity> findAll() {
        return userDao.findAll();
    }
}
