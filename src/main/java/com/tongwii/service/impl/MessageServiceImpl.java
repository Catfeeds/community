package com.tongwii.service.impl;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.core.BaseDao;
import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.MessageDao;
import com.tongwii.domain.MessageEntity;
import com.tongwii.service.IMessageService;
import com.tongwii.service.IPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */

@Service
@Transactional
public class MessageServiceImpl extends BaseServiceImpl<MessageEntity> implements IMessageService {
    @Autowired
    private MessageDao messageDao;
    private TongWIIResult result = new TongWIIResult();
    @Override
    public void save(MessageEntity messageEntity) {
        messageDao.save(messageEntity);
    }

    @Override
    public BaseDao<MessageEntity, String> getDao() {
        return messageDao;
    }

    @Override
    public void updateMessageProcess(String messageId, Integer processState) {
        boolean updateResult  = messageDao.updateMessageState(messageId, processState);
    }

    @Override
    public List<MessageEntity> selectMessageByType(IPageInfo pageInfo, String messageTypeId, String residenceId) {
        return messageDao.selectMessageByType(pageInfo, messageTypeId, residenceId);
    }
}
