package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 用户实体
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
@Entity
@Getter
@Setter
@Table(name = "user", schema = "cloud_community", catalog = "")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @NotNull
    @Column(name = "account")
    private String account;

    @Basic
    @NotNull
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "nick_name")
    private String nickName;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "sex")
    private Integer sex;

    @Basic
    @Column(name = "avatar_file_id")
    private String avatarFileId;

    @Basic
    @Column(name = "id_card")
    private String idCard;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "client_id")
    private String clientId;

    @Basic
    @Column(name = "birthday")
    private Date birthday;

    @Basic
    @Column(name = "signature")
    private String signature;

    @Basic
    @Column(name = "add_time")
    private Date addTime;

    @Basic
    @Column(name = "state")
    private Integer state;

//    @OneToMany(mappedBy = "userByChargeId")
//    private Collection<AreaEntity> areasById;
    @OneToMany(mappedBy = "userByUploadUserId")
    private Collection<File> filesById;

    @OneToMany(mappedBy = "userByPrincipalId")
    private Collection<Floor> floorsById;

    @OneToMany(mappedBy = "userByOperatorId")
    private Collection<Log> logsById;

    @OneToMany(mappedBy = "createUser")
    private Collection<Message> messagesById;

    @OneToMany(mappedBy = "userByCommentatorId")
    private Collection<MessageComment> messageCommentsById;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<Residence> residencesById;

    @OneToMany(mappedBy = "userByOwnerId")
    private Collection<Room> roomsById;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRole> userRolesById;

    @ManyToOne
    @JoinColumn(name = "avatar_file_id", referencedColumnName = "id", insertable = false, updatable = false)
    private File fileByAvatarFileId;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserContact> userContactsById;

    @OneToMany(mappedBy = "userByFriendId")
    private Collection<UserContact> userContactsById_0;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserGroup> userGroupsById;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoom> userRoomsById;
}
