package com.tongwii.dao;

import com.tongwii.core.BaseDao;
import com.tongwii.domain.ResidenceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class ResidenceDao extends BaseDao<ResidenceEntity, String> {

    /**
     * ï¿½ï¿½ï¿½ï¿½regionIdï¿½ï¿½Ñ¯residence
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
     * ï¿½ï¿½ï¿½ï¿½idï¿½ï¿½Ñ¯ï¿½ï¿½Â¼
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

    /**
     * ¸ù¾Ýcode²éÑ¯¼ÇÂ¼
     * @param code
     * @return ResidenceEntity
     * */
    public ResidenceEntity findByCode(String code){
        String hql = "from ResidenceEntity where code=? ";
        ResidenceEntity residenceEntity= findUniqueByHql(hql, code);
        if(residenceEntity == null){
            return null;
        }
        return residenceEntity;
    }
}
