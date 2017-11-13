package com.tongwii.service;

import com.tongwii.dao.IMessageCommentDao;
import com.tongwii.domain.MessageComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/10/23.
 */
@Service
@Transactional
public class MessageCommentService {
    private final IMessageCommentDao messageCommentDao;

    public MessageCommentService(IMessageCommentDao messageCommentDao) {
        this.messageCommentDao = messageCommentDao;
    }
    /**
     * 根据messageId获取点赞评论记录
     * @param messageId
     */
    public List<MessageComment> findByMessageIdAndType(String messageId, Integer type){
        return messageCommentDao.findByMessageIdAndType(messageId, type);
    }

    /**
     * 根据messageId与commentorId查询
     */
    public List<MessageComment> findByMessageIdAndCommentatorIdAndType(String messageId, String commentatorId, Integer type){
        return messageCommentDao.findByMessageIdAndCommentatorIdAndType(messageId, commentatorId, type);
    }

    /**
     * 添加点赞评论记录
     */
    public void addMessageComment(MessageComment messageComment){
        messageCommentDao.save(messageComment);
    }

    public Integer getCommentCounts(String id) {
       return messageCommentDao.countByMessageId(id);
    }
}
