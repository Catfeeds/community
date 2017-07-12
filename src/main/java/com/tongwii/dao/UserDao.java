package com.tongwii.dao;

import com.tongwii.po.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserDao extends BaseDao<UserEntity, String> {

    /**
     * 根据账号查询用户
     *
     * @param account
     * @return
     */
    public UserEntity findByAccount(String account) {
        String hql = "from UserEntity where account = ?";
        List<UserEntity> users = findByHQL(hql, account);
        if(CollectionUtils.isEmpty(users)) {
           return null;
        }
        return users.get(0);
    }

    /**
     * 更新用户头像文件
     *
     * @param userId
     * @param fileId
     */
    public void updateAvatorById(String userId, String fileId) {
        UserEntity userEntity = findById(userId);
        userEntity.setAvatarFileId(fileId);
        update(userEntity);
    }
}
