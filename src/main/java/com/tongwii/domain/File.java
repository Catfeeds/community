package com.tongwii.domain;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
@Table(name = "file", schema = "cloud_community", catalog = "")
public class File implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "file_name")
    private String fileName;

    @Basic
    @Column(name = "file_path")
    private String filePath;

    @Basic
    @Column(name = "file_type")
    private String fileType;

    @Basic
    @Column(name = "des")
    private String des;

    @Basic
    @Column(name = "state")
    private Byte state;

    @Basic
    @Column(name = "upload_user_id")
    private String uploadUserId;

    @ManyToOne
    @JoinColumn(name = "upload_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User userByUploadUserId;

    @OneToMany(mappedBy = "file")
    private Collection<Message> messagesById;

    @OneToMany(mappedBy = "fileByAvatarFileId")
    private Collection<User> usersById;
}