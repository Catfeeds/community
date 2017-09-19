package com.tongwii.service.impl;

import com.tongwii.core.BaseDao;
import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.RoomDao;
import com.tongwii.domain.RoomEntity;
import com.tongwii.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<RoomEntity> findRoomForChose(String unitId, String areaId) {
        return roomDao.findRoomForChose(unitId, areaId);
    }
}
