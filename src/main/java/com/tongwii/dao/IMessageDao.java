package com.tongwii.dao;

import com.tongwii.domain.MessageEntity;
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
public interface IMessageDao extends JpaRepository<MessageEntity, String> {
    @Modifying
    @Query("UPDATE MessageEntity m SET m.processState = :processState WHERE m.id = :messageId")
    void updateMessageState(String messageId, Integer processState);

    List<MessageEntity> findAllByMessageTypeIdAndResidenceId(Pageable pageable, String messageTypeId, String residenceId);

    @Query("SELECT m FROM MessageEntity m WHERE m.messageTypeId IN (3,6,7) and m.residenceId = :residenceId order by m.createTime desc")
    Page<MessageEntity> findByResidenceIdOrderByCreateTimeDesc(Pageable pageable, @Param("residenceId") String residenceId);

    List<MessageEntity> findByMessageTypeIdAndResidenceIdOrderByCreateTimeDesc(Pageable pageable, String messageTypeId, String residenceId);
}
