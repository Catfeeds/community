package com.tongwii.dao;

import com.tongwii.domain.UserRoom;
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
public interface IUserRoomDao extends JpaRepository<UserRoom, String> {
    List<UserRoom> findByRoomId(String roomId);

    List<UserRoom> findByUserId(String userId);
}
