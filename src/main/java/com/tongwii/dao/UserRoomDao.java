package com.tongwii.dao;

import com.tongwii.po.RoomEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.po.UserRoomEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRoomDao extends BaseDao<UserRoomEntity, String> {
    /**
     * 通过roomId查找用户信息
     *
     * @param roomId
     * @return userEntities
     * */
    public List<UserEntity> findUsersByRoomId(String roomId) {
        String hql = "from UserRoomEntity where roomByRoomId.id = ?";
        List<UserRoomEntity> userRoomEntities = findByHQL(hql, roomId);
        if(CollectionUtils.isEmpty(userRoomEntities)) {
           return null;
        }
        List<UserEntity> userEntities = new ArrayList<>();
        for (UserRoomEntity userRoomEntity : userRoomEntities) {
            userEntities.add(userRoomEntity.getUserByUserId());
        }
        return userEntities;
    }

    /**
     * 通过userId查找住房信息, 不排除一个用户有多个房间的情况
     *
     * @param userId the user id
     * @return roomEn room entity
     */
    public List<RoomEntity> findRoomByUserId(String userId) {
        String hql = "from UserRoomEntity where userId = ?";
        List<UserRoomEntity> userRoomEntities = findByHQL(hql, userId);
        List<RoomEntity> roomEntities = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(userRoomEntities)) {
            for (UserRoomEntity userRoomEntity: userRoomEntities) {
                roomEntities.add(userRoomEntity.getRoomByRoomId());
            }
        }
        return roomEntities;
    }
}
