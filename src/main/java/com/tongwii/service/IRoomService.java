package com.tongwii.service;

import com.tongwii.po.RoomEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IRoomService extends IBaseService<RoomEntity>{
    /**
     * 根据roomId查询一条记录
     *
     * @param roomCode
     * @return RoomEntity
     * */
    RoomEntity findRoomByCode(String roomCode);

    /**
     * 根据楼宇信息与单元号信息查询房间信息
     * @param unitId
     *
     * @return roomList
     * */
    List<RoomEntity> findRoomForChose(String unitId, String areaId);

    /**
     * 根据ID查询room表（仅有一条数据）
     * @param id
     *
     * @return roomEntity
     * */
    RoomEntity findById(String id);

}
