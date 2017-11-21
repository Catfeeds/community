package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 楼层实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@Getter
@Setter
@Table(name = "floor", schema = "cloud_community")
public class Floor implements Serializable {

    /**
     * The constant DONG. 常量-栋
     */
    public static final String DONG = "dong";
    /**
     * The constant UNIT. 常量-单元
     */
    public static final String UNIT = "unit";

    @Basic
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "piles")
    private String floorPiles;

    @Basic
    @Column(name = "elev")
    private Boolean elev;

    @Basic
    @Column(name = "principal_id")
    private String principalId;

    @Basic
    @Column(name = "residence_id")
    private String residenceId;

    @ManyToOne
    @JoinColumn(name = "principal_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByPrincipalId;

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Residence residence;

    @OneToMany(mappedBy = "floorByFloorId")
    private Collection<Room> roomsById;
}
