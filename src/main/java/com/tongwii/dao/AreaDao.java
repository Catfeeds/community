package com.tongwii.dao;

import com.tongwii.po.AreaEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class AreaDao extends BaseDao<AreaEntity, String> {
    /**
     * 根据residenceId查询area信息
     * @param residenceId
     * @return List
     * */
    public List<AreaEntity> findAreaByResidenceId(String residenceId){
        String hql = "from AreaEntity where residenceId = ? ";
        List<AreaEntity> areaEntities = findByHQL(hql, residenceId);
        if(CollectionUtils.isEmpty(areaEntities)) {
            return null;
        }
        return areaEntities;
    }

}
