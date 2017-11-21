package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 消息实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Setter
@Getter
@Table(name = "message", schema = "cloud_community")
public class Message implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "file_id")
    private String fileId;

    @Basic
    @Column(name = "residence_id")
    private String residenceId;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "create_user_id")
    private String createUserId;

    @Basic
    @Column(name = "message_type_id")
    private String messageTypeId;

    @Basic
    @Column(name = "process_state")
    private Integer processState;

    @Basic
    @Column(name = "repair_start_time")
    private Date repairStartTime;

    @Basic
    @Column(name = "repair_end_time")
    private Date repairEndTime;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", insertable = false, updatable = false)
    private File file;

    @ManyToOne
    @JoinColumn(name = "create_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User createUser;

    @ManyToOne
    @JoinColumn(name = "message_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MessageType messageType;

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Residence residence;

    @OneToMany(mappedBy = "messageByMessageId", fetch = FetchType.EAGER)
    private Collection<MessageComment> messageComments;
}
