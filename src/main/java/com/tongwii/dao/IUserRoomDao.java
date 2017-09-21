package com.tongwii.dao;

import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoomEntity;
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
public interface IUserRoomDao extends JpaRepository<UserRoomEntity, String> {
    List<UserEntity> findByRoomId(String roomId);

    List<UserRoomEntity> findByUserId(String userId);
}
