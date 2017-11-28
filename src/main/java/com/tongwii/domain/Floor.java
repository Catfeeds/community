package com.tongwii.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Floor implements Serializable {

    /**
     * The constant DONG. 常量-栋
     */
    public static final String DONG = "dong";
    /**
     * The constant UNIT. 常量-单元
     */
    public static final String UNIT = "unit";

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "code")
    private String code;

    /**
     * The Floor number.
     */
    @Column(name = "floor_number")
    private String floorNumber;

    /**
     * The Floor Is Have Elevator.
     */
    @Column(name = "has_Elev")
    private Boolean hasElev;

    @Column(name = "principal_id")
    private String principalId;

    @Column(name = "residence_id")
    private String residenceId;

    @ManyToOne
    @JoinColumn(name = "principal_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByPrincipalId;

    @ManyToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Residence residence;

    @OneToMany(mappedBy = "floorByFloorId")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Room> rooms;
}
