package com.tongwii.service;

import com.tongwii.dao.IUserRoomDao;
import com.tongwii.domain.UserRoom;
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

    public List<UserRoom> findUsersByRoomId(String roomId) {
        return userRoomDao.findByRoomId(roomId);
    }

    public List<UserRoom> findRoomByUserId(String userId) {
        return userRoomDao.findByUserId(userId);
    }

    public void saveSingle(UserRoom userRoom){
        userRoomDao.save(userRoom);
    }
}
