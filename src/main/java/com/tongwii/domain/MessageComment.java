package com.tongwii.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息评论
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "message_comment", schema = "cloud_community")
public class MessageComment implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "message_id")
    private String messageId;

    @Basic
    @Column(name = "is_like")
    private Boolean isLike;

    @Basic
    @Column(name = "comment")
    private String comment;

    @Basic
    @Column(name = "comment_date")
    private Date commentDate;

    @Basic
    @Column(name = "commentator_id")
    private String commentatorId;

    @Basic
    @Column(name = "type")
    private Integer type;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "message_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Message messageByMessageId;

    @ManyToOne
    @JoinColumn(name = "commentator_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByCommentatorId;
}
