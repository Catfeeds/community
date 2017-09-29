package com.tongwii.service;

import com.tongwii.dao.IRoomDao;
import com.tongwii.domain.RoomEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/14.
 */
@Service
@Transactional
public class RoomService {
    private final IRoomDao roomDao;

    public RoomService(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public RoomEntity findRoomByCode(String roomCode) {
        RoomEntity roomEntity = roomDao.findByRoomCode(roomCode);
        return roomEntity;
    }

    public List<RoomEntity> findByFloorId(String floorId) {
        return roomDao.findByFloorId(floorId);
    }

    public RoomEntity findById(String id) {
        return roomDao.findOne(id);
    }

    public void update(RoomEntity newRoom) {
        roomDao.save(newRoom);
    }

    public void save(RoomEntity roomEntity) { roomDao.save(roomEntity); }
}
