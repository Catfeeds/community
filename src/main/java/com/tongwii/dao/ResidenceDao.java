package com.tongwii.dao;

import com.tongwii.core.BaseDao;
import com.tongwii.domain.ResidenceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class ResidenceDao extends BaseDao<ResidenceEntity, String> {

    /**
     * ����regionId��ѯresidence
     * @param regionId
     * @return List
     * */
    public List<ResidenceEntity> findResidenceByRegionId(String regionId){
        String hql = "from ResidenceEntity where regionId = ?";
        List<ResidenceEntity> residenceEntities = findByHQL(hql, regionId);
        if(CollectionUtils.isEmpty(residenceEntities)){
            return null;
        }
        return residenceEntities;
    }

    /**
     * ����id��ѯ��¼
     * @param id
     * @return ResidenceEntity
     * */
    public ResidenceEntity findById(String id){
        String hql = "from ResidenceEntity where id=? ";
        ResidenceEntity residenceEntity= findUniqueByHql(hql, id);
        if(residenceEntity == null){
            return null;
        }
        return residenceEntity;
    }

}
