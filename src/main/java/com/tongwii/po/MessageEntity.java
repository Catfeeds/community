package com.tongwii.po;

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
    private Byte processState;
    private ResidenceEntity residenceByResidenceId;
    private Timestamp repairStartTime;
    private Timestamp repairEndTime;
    private FileEntity fileByFileId;
    private UserEntity userByCreateUserId;
    private MessageTypeEntity messageTypeByMessageTypeId;
    private Collection<MessageCommentEntity> messageCommentsById;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
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
    public Byte getProcessState() {
        return processState;
    }

    public void setProcessState(Byte processState) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUserId != null ? !createUserId.equals(that.createUserId) : that.createUserId != null) return false;
        if (messageTypeId != null ? !messageTypeId.equals(that.messageTypeId) : that.messageTypeId != null)
            return false;
        if (processState != null ? !processState.equals(that.processState) : that.processState != null) return false;
        if (repairStartTime != null ? !repairStartTime.equals(that.repairStartTime) : that.repairStartTime != null)
            return false;
        if (repairEndTime != null ? !repairEndTime.equals(that.repairEndTime) : that.repairEndTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUserId != null ? createUserId.hashCode() : 0);
        result = 31 * result + (messageTypeId != null ? messageTypeId.hashCode() : 0);
        result = 31 * result + (processState != null ? processState.hashCode() : 0);
        result = 31 * result + (repairStartTime != null ? repairStartTime.hashCode() : 0);
        result = 31 * result + (repairEndTime != null ? repairEndTime.hashCode() : 0);
        return result;
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

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)


    @OneToMany(mappedBy = "messageByMessageId")
    public Collection<MessageCommentEntity> getMessageCommentsById() {
        return messageCommentsById;
    }

    public void setMessageCommentsById(Collection<MessageCommentEntity> messageCommentsById) {
        this.messageCommentsById = messageCommentsById;
    }
}
