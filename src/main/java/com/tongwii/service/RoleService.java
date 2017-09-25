package com.tongwii.service;

import com.tongwii.dao.IRoleDao;
import com.tongwii.domain.RoleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Service
@Transactional
public class RoleService {

    private final IRoleDao roleDao;

    public RoleService(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public RoleEntity findRoleByCode(String roleCode) {
        RoleEntity roleEntity= roleDao.findByCode(roleCode);
        return roleEntity;
    }
}
