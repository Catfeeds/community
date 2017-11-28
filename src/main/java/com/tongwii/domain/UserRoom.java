package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户房间关系实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "user_room", schema = "cloud_community")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserRoom implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "user_id")
    private String userId; // 住户id

    @Basic
    @Column(name = "room_id")
    private String roomId;

    @Basic
    @Column(name = "type")
    private Integer type; // 住户类型（户主，租户，家庭成员）

    @Basic
    @Column(name = "des")
    private String des;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByUserId;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Room roomByRoomId;
}
