package com.tongwii.service;

import com.tongwii.constant.AuthoritiesConstants;
import com.tongwii.constant.UserConstants;
import com.tongwii.dao.IUserDao;
import com.tongwii.domain.File;
import com.tongwii.domain.Role;
import com.tongwii.domain.User;
import com.tongwii.domain.UserRole;
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
    private final IUserDao userDao;
    private final FileService fileService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDao userDao, FileService fileService, UserMapper userMapper, RoleService roleService, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    public UserDto save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAddTime(new Date());
        user.setState(UserConstants.USER_ENABLE);
        userDao.save(user);
        Role role = roleService.findRoleByCode(AuthoritiesConstants.USER);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleService.save(userRole);
        return userMapper.userToUserDTO(user);
    }

    public String updateUserAvatorById(String userId, MultipartFile multipartFile) {
        File file = fileService.saveAndUploadFileToFTP(userId, multipartFile);
        userDao.updateAvatorById(userId, file.getId());
        return file.getFilePath();
    }

    public User findById(String createUserId) {
        return userDao.findOne(createUserId);
    }

    public void update(User user) {
        userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
