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

    /**
     * 根据消息id修改消息处理状态
     *
     * @param messageId 消息id
     * @param processState 处理状态
     */
    @Modifying
    @Query("UPDATE Message m SET m.processState = :processState WHERE m.id = :messageId")
    void updateMessageStateByMessageId(String messageId, Integer processState);


    /**
     * <pre>
     *   根据社区id分页按创建时间逆序查询消息类型为 {@value com.tongwii.constant.MessageConstants#NOTIFY_MESSAGE}, {@value com.tongwii.constant.MessageConstants#HURRY_MESSAGE},
     *   {@value com.tongwii.constant.MessageConstants#NODE_MESSAGE} 且处理状态不为-1的消息
     * </pre>
     *
     * @param pageable 分页
     * @param residenceId 社区id
     *
     * @return
     */
    @Query("SELECT m FROM Message m WHERE m.messageType.code IN (com.tongwii.constant.MessageConstants"+
        ".NOTIFY_MESSAGE,com.tongwii.constant.MessageConstants.HURRY_MESSAGE,com.tongwii.constant.MessageConstants" +
        ".NODE_MESSAGE) and m.residence.id = :residenceId and m.processState <> -1 order by m.createTime desc")
    Page<Message> findByResidenceIdOrderByCreateTimeDesc(Pageable pageable, @Param("residenceId") String residenceId);

    /**
     * <pre>
     *   根据社区id分页按创建时间逆序查询消息类型为 {@value com.tongwii.constant.MessageConstants#NOTIFY_MESSAGE}, {@value com.tongwii.constant.MessageConstants#HURRY_MESSAGE},
     *   {@value com.tongwii.constant.MessageConstants#NODE_MESSAGE}, {@value com.tongwii.constant.MessageConstants#VOTE_MESSAGE} 且处理状态不为-1的消息
     * </pre>
     *
     * @param pageable 分页
     * @param residenceId 社区id
     * @return
     */
    @Query("SELECT m FROM Message m WHERE m.messageType.code IN (com.tongwii.constant.MessageConstants" +
        ".NOTIFY_MESSAGE, com.tongwii.constant.MessageConstants.VOTE_MESSAGE,com.tongwii.constant" +
        ".MessageConstants.HURRY_MESSAGE,com.tongwii.constant.MessageConstants.NODE_MESSAGE) and m.residence.id =" +
        " :residenceId and m.processState <> -1 order by m.createTime asc")
    Page<Message> findByResidenceIdOrderByCreateTimeAsc(Pageable pageable, @Param("residenceId") String residenceId);

    /**
     * 根据消息类型的Code和社区id通过创建时间逆序分页查询
     *
     * @param pageable 分页
     * @param messageTypeCode 消息类型code
     * @param residenceId 社区id
     * @return
     */
    Page<Message> findByMessageType_CodeAndResidence_IdOrderByCreateTimeDesc(Pageable pageable, String messageTypeCode, String residenceId);

    Message findById(String messageId);

    /**
     * 更新消息附件id
     *
     * @param messageId 消息id
     * @param fileId 附件id
     * @return
     */
    @Modifying
    @Query("UPDATE Message m SET m.fileId = :fileId WHERE m.id = :messageId")
    void updateMessageFileIdById(@Param(value = "messageId") String messageId, @Param(value = "fileId") String fileId);

    /**
     * 根据处理状态查询消息
     *
     * @param processState 处理状态
     * @return
     */
    List<Message> findByProcessState(int processState);
}
