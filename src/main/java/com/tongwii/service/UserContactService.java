package com.tongwii.service;

import com.tongwii.dao.IUserContactDao;
import com.tongwii.domain.UserContactEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
@Transactional
public class UserContactService {
    private final IUserContactDao userContactDao;

    public UserContactService(IUserContactDao userContactDao) {
        this.userContactDao = userContactDao;
    }

    public void addUserContact(UserContactEntity userContactEntity) {
        userContactDao.save(userContactEntity);
    }

    public List<UserContactEntity> findByUserId(String userId) {
        return userContactDao.findByUserId(userId);
    }

    public void delete(String id) {
        userContactDao.delete(id);
    }
}
