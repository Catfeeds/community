package com.tongwii.domain;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 设备唯一标识
 *
 * @author Zeral
 * @date 2017-11-21
 */
@Entity
@Data
@Table(name = "device", schema = "cloud_community")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device implements Serializable {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String deviceId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Device() {
    }

    public Device(String deviceId, User user) {
        this.deviceId = deviceId;
        this.user = user;
    }
}
