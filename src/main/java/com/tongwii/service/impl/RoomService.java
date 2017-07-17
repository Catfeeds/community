package com.tongwii.service.impl;

import com.tongwii.dao.BaseDao;
import com.tongwii.dao.RoomDao;
import com.tongwii.po.RoomEntity;
import com.tongwii.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/7/14.
 */
@Service
public class RoomService extends BaseServiceImpl<RoomEntity> implements IRoomService{
    @Autowired
    private RoomDao roomDao;
    @Override
    public RoomEntity findRoomByCode(String roomCode) {
        RoomEntity roomEntity = roomDao.findRoomsByRoomCode(roomCode);
        return roomEntity;
    }

    @Override
    public BaseDao getDao() {
        return roomDao;
    }
}
