package com.tongwii.service;

import com.tongwii.dao.IMessageTypeDao;
import com.tongwii.domain.MessageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息类型service
 *
 * @author Zeral
 * @date 2017-11-10
 */
@Service
@Transactional
public class MessageTypeService {
    private final IMessageTypeDao messageTypeDao;

    public MessageTypeService(IMessageTypeDao messageTypeDao) {
        this.messageTypeDao = messageTypeDao;
    }

    public MessageType findByCode(String code){
        return messageTypeDao.findByCode(code);
    }
}
