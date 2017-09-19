package com.tongwii.service;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.domain.MessageEntity;

/**
 * Created by admin on 2017/7/13.
 */
public interface IPushService {
    /**
     * ��Ϣ�����б�
     * @param pushInfo
     * @param roomCode
     * */
    TongWIIResult listMesssgePush(MessageEntity pushInfo, String roomCode);
}
