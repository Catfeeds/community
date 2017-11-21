package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
@Table(name = "log", schema = "cloud_community")
public class Log implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "record_date")
    private Timestamp recordDate;

    @Basic
    @Column(name = "operator_id")
    private String operatorId;

    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByOperatorId;
}
