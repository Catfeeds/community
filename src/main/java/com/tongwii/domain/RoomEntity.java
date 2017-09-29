package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 房间实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "room", schema = "cloud_community", catalog = "")
public class RoomEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "room_code")
    private String roomCode;
    @Basic
    @Column(name = "hu_xing")
    private String huXing;
    @Basic
    @Column(name = "client_code")
    private String clientCode;
    @Basic
    @Column(name = "owner_id")
    private String ownerId;
    @Basic
    @Column(name = "unit_code")
    private String unitCode;
    @Basic
    @Column(name = "floor_id")
    private String floorId;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userByOwnerId;
    @ManyToOne
    @JoinColumn(name = "floor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FloorEntity floorByFloorId;
    @OneToMany(mappedBy = "roomByRoomId")
    private Collection<UserRoomEntity> userRoomsById;
}
