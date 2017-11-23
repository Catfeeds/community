package com.tongwii.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 社区实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "residence", schema = "cloud_community")
public class Residence implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Basic
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "user_id")
    private String userId;

    @Basic
    @Column(name = "floor_count")
    private Integer floorCount;

    @Basic
    @Column(name = "region_code")
    private String regionCode;

    @Basic
    @Column(name = "address")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "residence")
    private Collection<Message> messageById;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByUserId;
}
