package com.tongwii.service;

import com.tongwii.dao.IMessageDao;
import com.tongwii.dao.IMessageTypeDao;
import com.tongwii.domain.Message;
import com.tongwii.domain.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;


/**
 * Created by admin on 2017/7/13.
 */

@Service
@Transactional
public class MessageService {
    private final IMessageDao messageDao;
    private final IMessageTypeDao messageTypeDao;

    public MessageService(IMessageDao messageDao, IMessageTypeDao messageTypeDao) {
        this.messageDao = messageDao;
        this.messageTypeDao = messageTypeDao;
    }

    public void save(Message message) {
        if(Objects.nonNull(message.getMessageType()) && !StringUtils.isEmpty(message.getMessageType().getCode())) {
            MessageType messageType = messageTypeDao.findByCode(message.getMessageType().getCode());
            message.setMessageTypeId(messageType.getId());
        }
        messageDao.save(message);
    }

    public void updateMessageProcess(String messageId, Integer processState) {
        Message message = messageDao.findById(messageId);
        message.setProcessState(processState);
    }


    public Page<Message> findByMessageTypeCodeAndResidenceIdOrderByCreateTimeDesc(Pageable pageable, String messageTypeCode, String residenceId) {
        return messageDao.findByMessageType_CodeAndResidenceIdOrderByCreateTimeDesc(pageable, messageTypeCode, residenceId);
    }

    /**
     * 查询公告类消息
     * */
    public Page<Message> findByResidenceIdOrderByCreateTimeDesc(Pageable pageable, String residenceId) {
        return messageDao.findByResidenceIdOrderByCreateTimeDesc(pageable, residenceId);
    }

    /**
     * 查询历史公告类消息
     * */
    public Page<Message> findByResidenceIdOrderByCreateTimeAsc(Pageable pageable, String residenceId) {
        return messageDao.findByResidenceIdOrderByCreateTimeAsc(pageable, residenceId);
    }

    public Message findByMessageId(String messageId){
        return messageDao.findById(messageId);
    }

}
