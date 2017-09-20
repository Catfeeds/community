package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * 消息实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "message", schema = "cloud_community", catalog = "")
public class MessageEntity implements Serializable {
    private String id;
    private String title;
    private String content;
    private String fileId;
    private String residenceId;
    private Timestamp createTime;
    private String createUserId;
    private String messageTypeId;
    private Integer processState;
    private ResidenceEntity residenceByResidenceId;
    private Timestamp repairStartTime;
    private Timestamp repairEndTime;
    private FileEntity fileByFileId;
    private UserEntity userByCreateUserId;
    private MessageTypeEntity messageTypeByMessageTypeId;
    private Collection<MessageCommentEntity> messageCommentsById;

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "file_id")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Basic
    @Column(name = "message_type_id")
    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    @Basic
    @Column(name = "process_state")
    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }

    @Basic
    @Column(name = "repair_start_time")
    public Timestamp getRepairStartTime() {
        return repairStartTime;
    }

    public void setRepairStartTime(Timestamp repairStartTime) {
        this.repairStartTime = repairStartTime;
    }

    @Basic
    @Column(name = "repair_end_time")
    public Timestamp getRepairEndTime() {
        return repairEndTime;
    }

    public void setRepairEndTime(Timestamp repairEndTime) {
        this.repairEndTime = repairEndTime;
    }

    @Basic
    @Column(name = "residence_id")
    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
    }

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", insertable = false, updatable = false)
    public FileEntity getFileByFileId() {
        return fileByFileId;
    }

    public void setFileByFileId(FileEntity fileByFileId) {
        this.fileByFileId = fileByFileId;
    }

    @ManyToOne
    @JoinColumn(name = "create_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByCreateUserId() {
        return userByCreateUserId;
    }

    public void setUserByCreateUserId(UserEntity userByCreateUserId) {
        this.userByCreateUserId = userByCreateUserId;
    }

    @ManyToOne
    @JoinColumn(name = "message_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    public MessageTypeEntity getMessageTypeByMessageTypeId() {
        return messageTypeByMessageTypeId;
    }

    public void setMessageTypeByMessageTypeId(MessageTypeEntity messageTypeByMessageTypeId) {
        this.messageTypeByMessageTypeId = messageTypeByMessageTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    public ResidenceEntity getResidenceByResidenceId() {
        return residenceByResidenceId;
    }

    public void setResidenceByResidenceId(ResidenceEntity residenceByResidenceId) {
        this.residenceByResidenceId = residenceByResidenceId;
    }

    @OneToMany(mappedBy = "messageByMessageId")
    public Collection<MessageCommentEntity> getMessageCommentsById() {
        return messageCommentsById;
    }

    public void setMessageCommentsById(Collection<MessageCommentEntity> messageCommentsById) {
        this.messageCommentsById = messageCommentsById;
    }
}
