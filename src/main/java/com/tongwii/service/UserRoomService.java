package com.tongwii.service;

import com.tongwii.dao.IUserRoomDao;
import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoomEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Service
@Transactional
public class UserRoomService {
    private final IUserRoomDao userRoomDao;

    public UserRoomService(IUserRoomDao userRoomDao) {
        this.userRoomDao = userRoomDao;
    }

    public List<UserRoomEntity> findUsersByRoomId(String roomId) {
        return userRoomDao.findByRoomId(roomId);
    }

    public List<UserRoomEntity> findRoomByUserId(String userId) {
        return userRoomDao.findByUserId(userId);
    }

    public void saveSingle(UserRoomEntity userRoomEntity){
        userRoomDao.save(userRoomEntity);
    }
}
