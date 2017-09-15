package com.tongwii.dao;

import com.tongwii.po.UserEntity;
import com.tongwii.po.UserRoomEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User room dao.
 */
@Repository
public class UserRoomDao extends BaseDao<UserRoomEntity, String> {
    /**
     * 通过roomId查找用户信息
     *
     * @param roomId the room id
     * @return userEntities list
     */
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
     * 通过userId查找住房信息
     *
     * @param userId the user id
     * @return roomEn room entity
     */
    public List<UserRoomEntity> findRoomByUserId(String userId) {
        String hql = "from UserRoomEntity where userId = ?";
        return findByHQL(hql, userId);
    }
}
