package com.tongwii.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tongwii.constant.MessageConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 消息实体
 *
 * @author Zeral
 * @date 2017/7/13
 */
@Entity
@Setter
@Getter
@Table(name = "message", schema = "cloud_community")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @NotNull
    private String id;

    @Column(name = "title")
    private String title;

    private String content;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user_id")
    private String createUserId;

    @Column(name = "process_state")
    private Integer processState = MessageConstants.UN_PROCESS;

    @Column(name = "repair_start_time")
    private Date repairStartTime;

    @Column(name = "repair_end_time")
    private Date repairEndTime;

    @Column(name = "residence_id")
    private String residenceId;

    @ManyToOne
    @JoinColumn(name = "message_type_id", referencedColumnName = "id")
    private MessageType messageType;

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Residence residence;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", insertable = false, updatable = false)
    private File file;

    @ManyToOne
    @JoinColumn(name = "create_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User createUser;

    @OneToMany(mappedBy = "message")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<MessageComment> messageComments;
}
