package com.tongwii.dao;

import com.tongwii.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IMessageDao extends JpaRepository<Message, String> {
    @Modifying
    @Query("UPDATE Message m SET m.processState = :processState WHERE m.id = :messageId")
    void updateMessageStateByMessageId(String messageId, Integer processState);

    List<Message> findAllByMessageTypeIdAndResidenceId(Pageable pageable, String messageTypeId, String residenceId);

    @Query("SELECT m FROM Message m WHERE m.messageTypeId IN (3,6,7) and m.residenceId = :residenceId and m.processState !=-1 order by m.createTime desc")
    Page<Message> findByResidenceIdOrderByCreateTimeDesc(Pageable pageable, @Param("residenceId") String residenceId);

    @Query("SELECT m FROM Message m WHERE m.messageTypeId IN (3,4,6,7) and m.residenceId = :residenceId and m.processState !=-1 order by m.createTime asc")
    Page<Message> findByResidenceIdOrderByCreateTimeAsc(Pageable pageable, @Param("residenceId") String residenceId);

    /**
     * 根据消息类型的Code和社区id通过创建时间逆序分页查询
     * @param pageable
     * @param messageTypeCode
     * @param residenceId
     * @return
     */

    Page<Message> findByMessageType_CodeAndResidenceIdOrderByCreateTimeDesc(Pageable pageable, String messageTypeCode, String residenceId);

    Message findById(String messageId);

    /**
     * 更新消息附件id
     * @param messageId 消息id
     * @param fileId 附件id
     * @return
     */
    @Modifying
    @Query("UPDATE Message m SET m.fileId = :fileId WHERE m.id = :messageId")
    void updateMessageFileIdById(@Param(value = "messageId") String messageId, @Param(value = "fileId") String fileId);
}
