package com.tongwii.service;

import com.tongwii.dao.IRoleDao;
import com.tongwii.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Role findRoleByCode(String roleCode) {
        Role role = roleDao.findByCode(roleCode);
        return role;
    }

    // 通过id查询role信息
    public Role findById(String roleId){
        return roleDao.findById(roleId);
    }

    // 获取所有角色信息
    public List<Role> findAll(){
        return roleDao.findAll();
    }
}
