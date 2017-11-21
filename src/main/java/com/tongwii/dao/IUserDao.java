package com.tongwii.dao;

import com.tongwii.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IUserDao extends JpaRepository<User, String> {
    User findByAccount(String account);

    @Modifying
    @Query("UPDATE User u SET u.avatarFileId = :id WHERE u.id = :userId")
    void updateAvatorById(@Param(value = "userId") String userId, @Param(value = "id") String id);

    Optional<User> findOneWithRolesByAccount(String account);

    Optional<User> findOneByAccount(String account);
}
