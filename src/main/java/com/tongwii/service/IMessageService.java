package com.tongwii.service;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.MessageEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IMessageService extends IBaseService<MessageEntity> {
    /**
     * 添加消息信息
     *
     * @param messageEntity
    * */
    @Override
    void save(MessageEntity messageEntity);

    /**
     * 根据messageId修改processState
     *
     * @param messageId
     * @param processState
     * */
    TongWIIResult updateMessageProcess(String messageId, Integer processState);

    /**
     * 根据消息类型查询消息
     *
     * @param messageTypeId
     * @return messageList
     * */
    List<MessageEntity> selectMessageByType(IPageInfo pageInfo, String messageTypeId);
}
