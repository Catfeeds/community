package com.tongwii.dao;

import com.tongwii.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IUserDao extends JpaRepository<UserEntity, String> {
    UserEntity findByAccount(String account);

    @Modifying
    @Query("UPDATE UserEntity u SET u.avatarFileId = :id WHERE u.id = :userId")
    void updateAvatorById(String userId, String id);
}
