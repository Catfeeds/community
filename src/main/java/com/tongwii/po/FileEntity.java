package com.tongwii.po;

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
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false, length = 32)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntity that = (FileEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;
        if (des != null ? !des.equals(that.des) : that.des != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (uploadUserId != null ? !uploadUserId.equals(that.uploadUserId) : that.uploadUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (des != null ? des.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (uploadUserId != null ? uploadUserId.hashCode() : 0);
        return result;
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
