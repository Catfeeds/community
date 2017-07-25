package com.tongwii.service.impl;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.dao.BaseDao;
import com.tongwii.dao.MessageDao;
import com.tongwii.po.MessageEntity;
import com.tongwii.service.IMessageService;
import com.tongwii.service.IPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */

@Service(value = "messageService")
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
    public TongWIIResult updateMessageProcess(String messageId, Integer processState) {
        boolean updateResult  = messageDao.updateMessageState(messageId, processState);
        if(updateResult == false){
            result.errorResult("修改信息失败!");
        }
        result.successResult("修改信息成功!");
        return result;
    }

    @Override
    public List<MessageEntity> selectMessageByType(IPageInfo pageInfo, String messageTypeId, String residenceId) {
        return messageDao.selectMessageByType(pageInfo, messageTypeId, residenceId);
    }
}
