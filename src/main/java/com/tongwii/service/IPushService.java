package com.tongwii.service;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.MessageEntity;
import com.tongwii.po.RoomEntity;
import com.tongwii.po.UserEntity;

/**
 * Created by admin on 2017/7/13.
 */
public interface IPushService {
    /**
     * 消息推送列表
     * @param pushInfo
     * @param roomCode
     * */
    TongWIIResult listMesssgePush(MessageEntity pushInfo, String roomCode);
}
