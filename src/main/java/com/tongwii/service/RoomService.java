package com.tongwii.service;

import com.tongwii.dao.IRoomDao;
import com.tongwii.domain.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/14.
 */
@Service
@Transactional
public class RoomService {

    private final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final IRoomDao roomDao;

    public RoomService(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Room findRoomByCode(String roomCode) {
        return roomDao.findByRoomCode(roomCode);
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

    /**
     * Save a room.
     *
     * @param room the entity to save
     * @return the persisted entity
     */
    public Room save(Room room) {
        log.debug("Request to save Room : {}", room);
        return roomDao.save(room);
    }

    /**
     * Get all the rooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Room> findAll(Pageable pageable) {
        log.debug("Request to get all Rooms");
        return roomDao.findAll(pageable);
    }

    /**
     * Get one room by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Room findOne(String id) {
        log.debug("Request to get Room : {}", id);
        return roomDao.findOne(id);
    }

    /**
     * Delete the room by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Room : {}", id);
        roomDao.delete(id);
    }
}
