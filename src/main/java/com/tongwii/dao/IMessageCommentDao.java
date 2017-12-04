package com.tongwii.dao;

import com.tongwii.domain.MessageComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息评论 JPA Dao接口
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IMessageCommentDao extends JpaRepository<MessageComment, String> {

    // 根据messageId查询消息评论记录
    List<MessageComment> findByMessageIdAndType(String messageId, int type);

    // 根据messageId与commentatorId查询消息点赞评论记录
    List<MessageComment> findByMessageIdAndCommentatorIdAndType(String messageId, String commentatorId, int type);

    // 根据消息id查询评论数
    int countByMessageId(String id);

    // 根据消息id和类型查询评论数量
    int countByMessageIdAndType(String messageId, int type);

    Page<MessageComment> findAllByMessageId(String messageId, Pageable pageable);
}
