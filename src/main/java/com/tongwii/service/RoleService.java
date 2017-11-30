package com.tongwii.service;

import com.tongwii.dao.IRoleDao;
import com.tongwii.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final IRoleDao roleDao;

    public RoleService(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role findRoleByCode(String roleCode) {
        return roleDao.findByCode(roleCode);
    }

    // 通过id查询role信息
    public Role findById(String roleId){
        return roleDao.findById(roleId);
    }

    // 获取所有角色信息
    public List<Role> findAll(){
        return roleDao.findAll();
    }

    /**
     * Save a role.
     *
     * @param role the entity to save
     * @return the persisted entity
     */
    public Role save(Role role) {
        log.debug("Request to save Role : {}", role);
        return roleDao.save(role);
    }

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Role> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return roleDao.findAll(pageable);
    }

    /**
     * Get one role by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Role findOne(String id) {
        log.debug("Request to get Role : {}", id);
        return roleDao.findOne(id);
    }

    /**
     * Delete the role by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Role : {}", id);
        roleDao.delete(id);
    }
}
