package com.tongwii.service;

import com.tongwii.po.RoomEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.po.UserRoomEntity;
import com.tongwii.service.IBaseService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IUserRoomService extends IBaseService<UserRoomEntity> {
    /**
     * 根据roomId查询userId
     *
     * @param roomId
     * return userIdList
     * */
   List<UserEntity> findUsersByRoomId(String roomId);

    /**
     * 根据userId查询roomId
     *
     * @param userId
     * return String
     * */
    String findRoomByUserId(String userId);

}
