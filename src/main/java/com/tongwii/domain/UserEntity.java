package com.tongwii.domain;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@Table(name = "user", schema = "cloud_community", catalog = "")
public class UserEntity implements Serializable {
    private String id;
    private String account;
    private String nickName;
    private Date birthday;
    private String signature;
    private Integer sex;
    private String avatarFileId;
    private String name;
    private String password;
    private String idCard;
    private String phone;
    private String clientId;
    private Date addTime;
    private Integer state;
    private Collection<AreaEntity> areasById;
    private Collection<FileEntity> filesById;
    private Collection<FloorEntity> floorsById;
    private Collection<LogEntity> logsById;
    private Collection<MessageEntity> messagesById;
    private Collection<MessageCommentEntity> messageCommentsById;
    private Collection<ResidenceEntity> residencesById;
    private Collection<RoomEntity> roomsById;
    private Collection<UserRoleEntity> userRolesById;
    private FileEntity fileByAvatarFileId;
    private Collection<UserContactEntity> userContactsById;
    private Collection<UserContactEntity> userContactsById_0;
    private Collection<UserGroupEntity> userGroupsById;
    private Collection<UserRoomEntity> userRoomsById;

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @NotNull
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Basic
    @Column(name = "sex")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "avatar_file_id")
    public String getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(String avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @NotNull
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "id_card")
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "client_id")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "userByChargeId")
    public Collection<AreaEntity> getAreasById() {
        return areasById;
    }

    public void setAreasById(Collection<AreaEntity> areasById) {
        this.areasById = areasById;
    }

    @OneToMany(mappedBy = "userByUploadUserId")
    public Collection<FileEntity> getFilesById() {
        return filesById;
    }

    public void setFilesById(Collection<FileEntity> filesById) {
        this.filesById = filesById;
    }

    @OneToMany(mappedBy = "userByPrincipalId")
    public Collection<FloorEntity> getFloorsById() {
        return floorsById;
    }

    public void setFloorsById(Collection<FloorEntity> floorsById) {
        this.floorsById = floorsById;
    }

    @OneToMany(mappedBy = "userByOperatorId")
    public Collection<LogEntity> getLogsById() {
        return logsById;
    }

    public void setLogsById(Collection<LogEntity> logsById) {
        this.logsById = logsById;
    }

    @OneToMany(mappedBy = "userByCreateUserId")
    public Collection<MessageEntity> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(Collection<MessageEntity> messagesById) {
        this.messagesById = messagesById;
    }

    @OneToMany(mappedBy = "userByCommentatorId")
    public Collection<MessageCommentEntity> getMessageCommentsById() {
        return messageCommentsById;
    }

    public void setMessageCommentsById(Collection<MessageCommentEntity> messageCommentsById) {
        this.messageCommentsById = messageCommentsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<ResidenceEntity> getResidencesById() {
        return residencesById;
    }

    public void setResidencesById(Collection<ResidenceEntity> residencesById) {
        this.residencesById = residencesById;
    }

    @OneToMany(mappedBy = "userByOwnerId")
    public Collection<RoomEntity> getRoomsById() {
        return roomsById;
    }

    public void setRoomsById(Collection<RoomEntity> roomsById) {
        this.roomsById = roomsById;
    }

    @ManyToOne
    @JoinColumn(name = "avatar_file_id", referencedColumnName = "id", insertable = false, updatable = false)
    public FileEntity getFileByAvatarFileId() {
        return fileByAvatarFileId;
    }

    public void setFileByAvatarFileId(FileEntity fileByAvatarFileId) {
        this.fileByAvatarFileId = fileByAvatarFileId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserContactEntity> getUserContactsById() {
        return userContactsById;
    }

    public void setUserContactsById(Collection<UserContactEntity> userContactsById) {
        this.userContactsById = userContactsById;
    }

    @OneToMany(mappedBy = "userByFriendId")
    public Collection<UserContactEntity> getUserContactsById_0() {
        return userContactsById_0;
    }

    public void setUserContactsById_0(Collection<UserContactEntity> userContactsById_0) {
        this.userContactsById_0 = userContactsById_0;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserGroupEntity> getUserGroupsById() {
        return userGroupsById;
    }

    public void setUserGroupsById(Collection<UserGroupEntity> userGroupsById) {
        this.userGroupsById = userGroupsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserRoleEntity> getUserRolesById() {
        return userRolesById;
    }

    public void setUserRolesById(Collection<UserRoleEntity> userRolesById) {
        this.userRolesById = userRolesById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserRoomEntity> getUserRoomsById() {
        return userRoomsById;
    }

    public void setUserRoomsById(Collection<UserRoomEntity> userRoomsById) {
        this.userRoomsById = userRoomsById;
    }
}
