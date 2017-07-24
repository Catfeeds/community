package com.tongwii.dao;

import com.tongwii.po.UserEntity;
import com.tongwii.po.UserRoomEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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
}
