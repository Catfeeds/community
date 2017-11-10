package com.tongwii.service;

import com.tongwii.dao.IUserContactDao;
import com.tongwii.domain.UserContact;
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

    public void addUserContact(UserContact userContact) {
        userContactDao.save(userContact);
    }

    public List<UserContact> findByUserId(String userId) {
        return userContactDao.findByUserId(userId);
    }

    public void delete(String id) {
        userContactDao.delete(id);
    }
}
