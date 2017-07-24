package com.tongwii.service.impl;

import com.tongwii.dao.BaseDao;
import com.tongwii.dao.UserContactDao;
import com.tongwii.po.UserContactEntity;
import com.tongwii.service.IUserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
public class UserContactServiceImpl extends BaseServiceImpl<UserContactEntity> implements IUserContactService {
    @Autowired
    private UserContactDao userContactDao;
    @Override
    public BaseDao<UserContactEntity, String> getDao() {
        return userContactDao;
    }

    @Override
    public void addUserContact(UserContactEntity userContactEntity) {
        userContactDao.save(userContactEntity);
    }

    @Override
    public List<UserContactEntity> findByUserId(String userId) {
        return userContactDao.findByUserId(userId);
    }

    @Override
    public void delete(String id) {
        super.delete(id);
    }
}
