package com.tongwii.dao;

import com.tongwii.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IRoleDao extends JpaRepository<RoleEntity, String> {
    RoleEntity findByCode(String roleCode);
    RoleEntity findById(String roleId);
}
