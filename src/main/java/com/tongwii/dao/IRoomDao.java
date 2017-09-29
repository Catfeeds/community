package com.tongwii.dao;

import com.tongwii.domain.RoomEntity;
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
public interface IRoomDao extends JpaRepository<RoomEntity, String> {
    RoomEntity findByRoomCode(String roomCode);

    List<RoomEntity> findByFloorId(String floorId);
}
