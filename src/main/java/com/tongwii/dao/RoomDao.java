package com.tongwii.dao;

import com.tongwii.core.BaseDao;
import com.tongwii.domain.RoomEntity;
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
        String hql = "from RoomEntity where roomCode = ? ";
        RoomEntity r = findUniqueByHql(hql,roomCode);
        return r;
    }

    /**
     * 根据楼宇信息与单元号信息查询房间信息
     * @param unitId
     * @param areaId
     * @return roomList
     * */
    public List<RoomEntity> findRoomForChose(String unitId, String areaId){
        String hql = "from RoomEntity room where room.area=? and room.unitId=?";
        Double area = Double.valueOf(areaId);
        List<RoomEntity> roomEntities = findByHQL(hql, area, unitId);
        if(CollectionUtils.isEmpty(roomEntities)) {
            return null;
        }
        return roomEntities;
    }
}
