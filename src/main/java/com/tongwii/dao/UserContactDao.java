package com.tongwii.dao;

import com.tongwii.core.BaseDao;
import com.tongwii.domain.UserContactEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserContactDao extends BaseDao<UserContactEntity, String> {
    /**
     * 根据userId查询UserContact表
     * @param userId
     * @return List
     * */
    public List<UserContactEntity> findByUserId(String userId){
        String hql = " from UserContactEntity where userId =? ";
        List<UserContactEntity> userContactEntities = findByHQL(hql, userId);
        if(CollectionUtils.isEmpty(userContactEntities)){
            return null;
        }
        return userContactEntities;
    }
	
}
