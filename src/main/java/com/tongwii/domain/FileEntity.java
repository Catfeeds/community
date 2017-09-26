package com.tongwii.domain;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 文件实体
 *
 * @author: Zeral
 * @date: 2017/7/13
 */
@Entity
@EqualsAndHashCode
@Table(name = "file", schema = "cloud_community", catalog = "")
public class FileEntity implements Serializable {
    private String id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String des;
    private Byte state;
    private String uploadUserId;
    private UserEntity userByUploadUserId;
    private Collection<MessageEntity> messagesById;
    private Collection<UserEntity> usersById;

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
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "des")
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Basic
    @Column(name = "state")
    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "upload_user_id")
    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    @ManyToOne
    @JoinColumn(name = "upload_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserEntity getUserByUploadUserId() {
        return userByUploadUserId;
    }

    public void setUserByUploadUserId(UserEntity userByUploadUserId) {
        this.userByUploadUserId = userByUploadUserId;
    }

    @OneToMany(mappedBy = "fileByFileId")
    public Collection<MessageEntity> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(Collection<MessageEntity> messagesById) {
        this.messagesById = messagesById;
    }

    @OneToMany(mappedBy = "fileByAvatarFileId")
    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }
}
