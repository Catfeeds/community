package com.tongwii.service;

import com.tongwii.core.IBaseService;
import com.tongwii.domain.MessageEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public interface IMessageService extends IBaseService<MessageEntity> {
    /**
     *
     * @param messageEntity
    * */
    @Override
    void save(MessageEntity messageEntity);

    /**
     *
     * @param messageId
     * @param processState
     * */
    void updateMessageProcess(String messageId, Integer processState);

    /**
     *
     * @param messageTypeId
     * @param pageInfo
     * @param residenceId
     * @return messageList
     * */
    List<MessageEntity> selectMessageByType(IPageInfo pageInfo, String messageTypeId, String residenceId);
}
