package com.tongwii.domain;

import com.tongwii.constant.Constants;
import com.tongwii.constant.UserConstants;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
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
public class User implements Serializable {
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

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "sex", nullable = false)
    private Integer sex = UserConstants.USER_SEX_MALE;      // 性别默认男

    @Column(name = "avatar_file_id")
    private String avatarFileId;

    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;

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
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @BatchSize(size = 5)
    private Set<Device> devices = new HashSet<>();

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

    @ManyToOne
    @JoinColumn(name = "avatar_file_id", referencedColumnName = "id", insertable = false, updatable = false)
    private File fileByAvatarFileId;

    @OneToMany(mappedBy = "userByFriendId")
    private Collection<UserContact> userContactsById;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserGroup> userGroupsById;

    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserRoom> userRoomsById;
}
