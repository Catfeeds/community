package com.tongwii.service;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.MessageEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IMessageService extends IBaseService<MessageEntity> {
    /**
     * ������Ϣ��Ϣ
     *
     * @param messageEntity
    * */
    @Override
    void save(MessageEntity messageEntity);

    /**
     * ����messageId�޸�processState
     *
     * @param messageId
     * @param processState
     * */
    TongWIIResult updateMessageProcess(String messageId, Integer processState);

    /**
     * ������Ϣ���Ͳ�ѯ��Ϣ
     *
     * @param messageTypeId
     * @return messageList
     * */
    List<MessageEntity> selectMessageByType(IPageInfo pageInfo, String messageTypeId);
}