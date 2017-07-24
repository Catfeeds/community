package com.tongwii.dao;

import com.tongwii.po.ResidenceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class ResidenceDao extends BaseDao<ResidenceEntity, String> {

    /**
     * 根据regionId查询residence
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
     * 根据id查询记录
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
