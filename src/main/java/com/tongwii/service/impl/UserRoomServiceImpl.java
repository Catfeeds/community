package com.tongwii.service.impl;

import com.tongwii.dao.BaseDao;
import com.tongwii.dao.UserRoomDao;
import com.tongwii.po.UserEntity;
import com.tongwii.po.UserRoomEntity;
import com.tongwii.service.IUserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Service(value = "userRoomService")
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
