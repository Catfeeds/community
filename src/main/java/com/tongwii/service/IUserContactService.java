package com.tongwii.service;

import com.tongwii.domain.UserContactEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IUserContactService {

    /**
     * �����ϵ�˽ӿ�
     * @param userContactEntity
     * @return void
     * */
    void addUserContact(UserContactEntity userContactEntity);

    /**
     * ����userId��ѯUserContact��
     * @param userId
     * @return
     * */
    List<UserContactEntity> findByUserId(String userId);

    /**
     * ɾ����ϵ����Ϣ
     * */
    void delete(String id);
}
