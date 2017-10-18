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
        MessageEntity messageEntity = messageDao.findById(messageId);
        messageEntity.setProcessState(processState);
//        messageDao.updateMessageStateByMessageId(messageId, processState);
    }


    public List<MessageEntity> findByMessageTypeIdAndResidenceIdOrderByCreateTimeDesc(Pageable pageable, String messageTypeId, String residenceId) {
        return messageDao.findByMessageTypeIdAndResidenceIdOrderByCreateTimeDesc(pageable, messageTypeId, residenceId);
    }

    /**
     * 查询公告类消息
     * */
    public Page<MessageEntity> findByResidenceIdOrderByCreateTimeDesc(Pageable pageable, String residenceId) {
        return messageDao.findByResidenceIdOrderByCreateTimeDesc(pageable, residenceId);
    }

    /**
     * 查询历史公告类消息
     * */
    public Page<MessageEntity> findByResidenceIdOrderByCreateTimeAsc(Pageable pageable, String residenceId) {
        return messageDao.findByResidenceIdOrderByCreateTimeAsc(pageable, residenceId);
    }

}
