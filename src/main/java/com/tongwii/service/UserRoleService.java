package com.tongwii.service;

import com.tongwii.dao.IUserRoleDao;
import com.tongwii.domain.UserRoleEntity;
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
public class UserRoleService {
    private final IUserRoleDao userRoleDao;

    public UserRoleService(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public void save(UserRoleEntity userRoleEntity) {
        userRoleDao.save(userRoleEntity);
    }
}
