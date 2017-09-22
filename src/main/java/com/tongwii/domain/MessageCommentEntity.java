package com.tongwii.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消息评论
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Table(name = "message_comment", schema = "cloud_community", catalog = "")
public class MessageCommentEntity implements Serializable {
    private String id;
    private String messageId;
    private Boolean isLike;
    private String comment;
    private Timestamp commentDate;
    private String commentatorId;
    private MessageEntity messageByMessageId;
    private UserEntity userByCommentatorId;

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message_id")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "is_like")
    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "comment_date")
    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    @Basic
    @Column(name = "commentator_id")
    public String getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(String commentatorId) {
        this.commentatorId = commentatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageCommentEntity that = (MessageCommentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (isLike != null ? !isLike.equals(that.isLike) : that.isLike != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (commentDate != null ? !commentDate.equals(that.commentDate) : that.commentDate != null) return false;
        if (commentatorId != null ? !commentatorId.equals(that.commentatorId) : that.commentatorId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (isLike != null ? isLike.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        result = 31 * result + (commentatorId != null ? commentatorId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false)
    public MessageEntity getMessageByMessageId() {
        return messageByMessageId;
    }

    public void setMessageByMessageId(MessageEntity messageByMessageId) {
        this.messageByMessageId = messageByMessageId;
    }

    @ManyToOne
    @JoinColumn(name = "commentator_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByCommentatorId() {
        return userByCommentatorId;
    }

    public void setUserByCommentatorId(UserEntity userByCommentatorId) {
        this.userByCommentatorId = userByCommentatorId;
    }
}
