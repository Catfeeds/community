package com.tongwii.service.impl;

import com.tongwii.core.BaseDao;
import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.UserContactDao;
import com.tongwii.domain.UserContactEntity;
import com.tongwii.service.IUserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
@Transactional
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
