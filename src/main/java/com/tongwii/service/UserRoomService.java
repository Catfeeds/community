package com.tongwii.service;

import com.tongwii.dao.IUserRoomDao;
import com.tongwii.domain.UserRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Service
@Transactional
public class UserRoomService {

    private final Logger log = LoggerFactory.getLogger(UserRoomService.class);

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


    /**
     * Save a userRoom.
     *
     * @param userRoom the entity to save
     * @return the persisted entity
     */
    public UserRoom save(UserRoom userRoom) {
        log.debug("Request to save UserRoom : {}", userRoom);
        return userRoomDao.save(userRoom);
    }

    /**
     * Get all the userRooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserRoom> findAll(Pageable pageable) {
        log.debug("Request to get all UserRooms");
        return userRoomDao.findAll(pageable);
    }

    /**
     * Get one userRoom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserRoom findOne(String id) {
        log.debug("Request to get UserRoom : {}", id);
        return userRoomDao.findOne(id);
    }

    /**
     * Delete the userRoom by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete UserRoom : {}", id);
        userRoomDao.delete(id);
    }
}
