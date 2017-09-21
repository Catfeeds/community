package com.tongwii.service.impl;

import com.tongwii.core.BaseDao;
import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.UserRoomDao;
import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoomEntity;
import com.tongwii.service.IUserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Service
@Transactional
public class UserRoomServiceImpl extends BaseServiceImpl<UserRoomEntity> implements IUserRoomService {
    @Autowired
    private UserRoomDao userRoomDao;

    @Override
    public BaseDao<UserRoomEntity, String> getDao() {
        return userRoomDao;
    }

    @Override
    public List<UserEntity> findUsersByRoomId(String roomId) {
        return userRoomDao.findUsersByRoomId(roomId);
    }

    @Override
    public List<UserRoomEntity> findRoomByUserId(String userId) {
        return userRoomDao.findRoomByUserId(userId);
    }
}
