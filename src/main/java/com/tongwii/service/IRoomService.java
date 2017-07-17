package com.tongwii.service;

import com.tongwii.po.RoomEntity;

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
    public RoomEntity findRoomByCode(String roomCode);

}
