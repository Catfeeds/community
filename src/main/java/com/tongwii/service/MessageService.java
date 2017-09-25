package com.tongwii.service;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.dao.IMessageDao;
import com.tongwii.domain.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by admin on 2017/7/13.
 */

@Service
@Transactional
public class MessageService {
    private final IMessageDao messageDao;

    private TongWIIResult result = new TongWIIResult();

    public MessageService(IMessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void save(MessageEntity messageEntity) {
        messageDao.save(messageEntity);
    }

    public void updateMessageProcess(String messageId, Integer processState) {
        messageDao.updateMessageState(messageId, processState);
    }

    public List<MessageEntity> selectMessageByType(Pageable pageable, String messageTypeId, String residenceId) {
        return messageDao.findAllByMessageTypeIdAndResidenceId(pageable, messageTypeId, residenceId);
    }

    /**
     * 查询公告类消息
     * */
    public Page<MessageEntity> selectAnnounceMessage(Pageable pageable, String residenceId) {
        return messageDao.findAnnounceByResidenceId(pageable, residenceId);
    }
}
