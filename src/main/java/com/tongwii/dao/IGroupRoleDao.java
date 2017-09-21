package com.tongwii.dao;

import com.tongwii.domain.GroupRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IGroupRoleDao extends JpaRepository<GroupRoleEntity, String> {
}
