package com.tongwii.service;

import com.tongwii.po.UserContactEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IUserContactService {

    /**
     * 添加联系人接口
     * @param userContactEntity
     * @return void
     * */
    void addUserContact(UserContactEntity userContactEntity);

    /**
     * 根据userId查询UserContact表
     * @param userId
     * @return
     * */
    List<UserContactEntity> findByUserId(String userId);

    /**
     * 删除联系人信息
     * */
    void delete(String id);
}
