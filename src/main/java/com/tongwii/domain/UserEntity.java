package com.tongwii.domain;

import lombok.Data;
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
public class UserEntity implements Serializable {
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
    @Column(name = "nick_name")
    private String nickName;
    @Basic
    @Column(name = "birthday")
    private Date birthday;
    @Basic
    @Column(name = "signature")
    private String signature;
    @Basic
    @Column(name = "sex")
    private Integer sex;
    @Basic
    @Column(name = "avatar_file_id")
    private String avatarFileId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @NotNull
    @Column(name = "password")
    private String password;
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
    @Column(name = "add_time")
    private Date addTime;
    @Basic
    @Column(name = "state")
    private Integer state;
//    @OneToMany(mappedBy = "userByChargeId")
//    private Collection<AreaEntity> areasById;
    @OneToMany(mappedBy = "userByUploadUserId")
    private Collection<FileEntity> filesById;
    @OneToMany(mappedBy = "userByPrincipalId")
    private Collection<FloorEntity> floorsById;
    @OneToMany(mappedBy = "userByOperatorId")
    private Collection<LogEntity> logsById;
    @OneToMany(mappedBy = "userByCreateUserId")
    private Collection<MessageEntity> messagesById;
    @OneToMany(mappedBy = "userByCommentatorId")
    private Collection<MessageCommentEntity> messageCommentsById;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<ResidenceEntity> residencesById;
    @OneToMany(mappedBy = "userByOwnerId")
    private Collection<RoomEntity> roomsById;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoleEntity> userRolesById;
    @ManyToOne
    @JoinColumn(name = "avatar_file_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FileEntity fileByAvatarFileId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserContactEntity> userContactsById;
    @OneToMany(mappedBy = "userByFriendId")
    private Collection<UserContactEntity> userContactsById_0;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserGroupEntity> userGroupsById;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoomEntity> userRoomsById;
}
