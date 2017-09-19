package com.tongwii.service;

import com.tongwii.core.IBaseService;
import com.tongwii.domain.RoomEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IRoomService extends IBaseService<RoomEntity> {
    /**
     * ����roomId��ѯһ����¼
     *
     * @param roomCode
     * @return RoomEntity
     * */
    RoomEntity findRoomByCode(String roomCode);

    /**
     * ����¥����Ϣ�뵥Ԫ����Ϣ��ѯ������Ϣ
     * @param unitId
     *
     * @return roomList
     * */
    List<RoomEntity> findRoomForChose(String unitId, String areaId);

    /**
     * ����ID��ѯroom������һ�����ݣ�
     * @param id
     *
     * @return roomEntity
     * */
    RoomEntity findById(String id);

}
