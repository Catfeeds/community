package com.tongwii.domain;

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
    @Column(name = "id", unique = true, nullable = false, length = 32)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntity logEntity = (LogEntity) o;

        if (id != null ? !id.equals(logEntity.id) : logEntity.id != null) return false;
        if (content != null ? !content.equals(logEntity.content) : logEntity.content != null) return false;
        if (recordDate != null ? !recordDate.equals(logEntity.recordDate) : logEntity.recordDate != null) return false;
        if (operatorId != null ? !operatorId.equals(logEntity.operatorId) : logEntity.operatorId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (recordDate != null ? recordDate.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        return result;
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
