package com.tongwii.dao;

import com.tongwii.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IRoomDao extends JpaRepository<Room, String> {
    Room findByRoomCode(String roomCode);

    List<Room> findByFloorId(String floorId);
}
