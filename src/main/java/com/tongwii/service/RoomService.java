package com.tongwii.service;

import com.tongwii.dao.IRoomDao;
import com.tongwii.domain.Room;
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

    public Room findRoomByCode(String roomCode) {
        Room room = roomDao.findByRoomCode(roomCode);
        return room;
    }

    public List<Room> findByFloorId(String floorId) {
        return roomDao.findByFloorId(floorId);
    }

    public Room findById(String id) {
        return roomDao.findOne(id);
    }

    public void update(Room newRoom) {
        roomDao.save(newRoom);
    }

    public void save(Room room) { roomDao.save(room); }
}
