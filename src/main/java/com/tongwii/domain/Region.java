package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地区实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "region", schema = "cloud_community", catalog = "")
public class Region implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "region_code")
    private String regionCode;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "parent_code")
    private String parentCode;

    @Basic
    @Column(name = "level")
    private Integer level;
}
