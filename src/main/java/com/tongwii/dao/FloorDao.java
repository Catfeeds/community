package com.tongwii.dao;

import com.tongwii.po.FloorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class FloorDao extends BaseDao<FloorEntity, String> {
	/**
     * ����areaId��ѯ
     * @param areaId
     * @return List
     * */
    public List<FloorEntity> findFloorByAreId(String areaId){
        String hql = "from FloorEntity where areaId=?";
        List<FloorEntity> floorEntities = findByHQL(hql,areaId);
        if(CollectionUtils.isEmpty(floorEntities)) {
            return null;
        }
        return floorEntities;
    }
}
