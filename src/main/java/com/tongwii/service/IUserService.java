package com.tongwii.service;

import com.tongwii.core.IBaseService;
import com.tongwii.domain.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Author: Zeral
 * Date: 2017/7/11
 */
public interface IUserService extends IBaseService<UserEntity> {
    /**
     * 根据账户查找用户
     *
     * @param account 用户账号
     * @return UserEntity 用户
     */
    UserEntity findByAccount(String account);

    /**
     * 添加用户，将密码加密
     *
     * @param userEntity 添加的用户
     */
    void save(UserEntity userEntity);

    /**
     * 上传用户头像并更新用户头像地址
     *
     * @param userId 用户id
     * @param file   上传文件
     * @return String 上传文件地址
     * @throws IOException the io exception
     */
    String updateUserAvatorById(String userId, MultipartFile file);
}
