package com.tongwii.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tongwii.constant.Constants;
import com.tongwii.constant.UserConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
@Entity
@Getter
@Setter
@Table(name = "user", schema = "cloud_community")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String account;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60)
    private String password;

    @Column(name = "nick_name", length = 50)
    private String nickName;

    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Column(name = "sex", nullable = false)
    private Integer sex = UserConstants.USER_SEX_MALE;      // 性别默认男

    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "signature")
    private String signature;

    @Column(name = "add_time")
    private Date addTime;

    @NotNull
    @Column(nullable = false)
    private boolean activated = true;

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 5)
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "uploadUser")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<File> files;

    @OneToMany(mappedBy = "userByPrincipalId")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Floor> floors;

    @OneToMany(mappedBy = "userByOperatorId")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Log> logs;

    @OneToMany(mappedBy = "createUser")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Message> messages;

    @OneToMany(mappedBy = "commentator")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<MessageComment> messageComments;

    @OneToMany(mappedBy = "chargeUser")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Residence> residences;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<Room> rooms;

    @OneToMany(mappedBy = "userByFriendId")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<UserContact> userContacts;

    @OneToMany(mappedBy = "userByUserId")
    @JsonIgnore
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Collection<UserRoom> userRooms;
}
