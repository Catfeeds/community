package com.tongwii.dao;

import com.tongwii.po.RoomEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class RoomDao extends BaseDao<RoomEntity, String> {
    /**
     * 通过roomCode查找room信息
     * @param roomCode
     * @return roomEntities
     * */
    public RoomEntity findRoomsByRoomCode(String roomCode){
//        String hql = "from UserRoomEntity where roomByRoomId.id = ?";
        String hql1 = "from RoomEntity where roomCode = ? ";
        List<RoomEntity> roomEntities = findByHQL(hql1, roomCode);

        if(CollectionUtils.isEmpty(roomEntities)) {
            return null;
        }
        return roomEntities.get(0);
    }
}
