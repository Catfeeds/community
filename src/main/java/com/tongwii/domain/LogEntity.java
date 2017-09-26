package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 日志实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "log", schema = "cloud_community", catalog = "")
public class LogEntity implements Serializable {
    private String id;
    private String content;
    private Timestamp recordDate;
    private String operatorId;
    private UserEntity userByOperatorId;

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
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "record_date")
    public Timestamp getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
    }

    @Basic
    @Column(name = "operator_id")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByOperatorId() {
        return userByOperatorId;
    }

    public void setUserByOperatorId(UserEntity userByOperatorId) {
        this.userByOperatorId = userByOperatorId;
    }
}
