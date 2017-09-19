package com.tongwii.service;

import com.tongwii.core.IBaseService;
import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoomEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IUserRoomService extends IBaseService<UserRoomEntity> {
    /**
     * 根据roomId查询userId
     *
     * @param roomId return userIdList
     * @return the list
     */
    List<UserEntity> findUsersByRoomId(String roomId);

    /**
     * 根据userId查询room
     *
     * @param userId return String
     * @return the list
     */
    List<UserRoomEntity> findRoomByUserId(String userId);

}
