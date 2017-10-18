package com.tongwii.service;

import com.tongwii.dao.IUserRoleDao;
import com.tongwii.domain.UserRoleEntity;
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
public class UserRoleService {
    private final IUserRoleDao userRoleDao;

    public UserRoleService(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public void save(UserRoleEntity userRoleEntity) {
        userRoleDao.save(userRoleEntity);
    }

    // 获取用户角色关系列表
    public List<UserRoleEntity> findByUserId(String userId){
        return userRoleDao.findByUserId(userId);
    }

    public void delete(UserRoleEntity userRoleEntity){
        userRoleDao.delete(userRoleEntity);
    }
}
