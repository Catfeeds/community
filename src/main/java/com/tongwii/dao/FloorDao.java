package com.tongwii.dao;

import com.tongwii.core.BaseDao;
import com.tongwii.domain.FloorEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The type Floor dao.
 */
@Repository
public class FloorDao extends BaseDao<FloorEntity, String> {
    /**
     * 根据areaId查询楼宇信息
     *
     * @param areaId the area id
     * @return List list
     */
    public List<FloorEntity> findFloorByAreId(String areaId){
        String hql = "from FloorEntity where areaId=?";
        List<FloorEntity> floorEntities = findByHQL(hql,areaId);
        if(CollectionUtils.isEmpty(floorEntities)) {
            return null;
        }
        return floorEntities;
    }

    /**
     * 根据code查询楼宇信息
     *
     * @param code the code
     * @return the list
     */
    public FloorEntity findFloorByCode(String code) {
        String hql = "from FloorEntity where code = ?";
        return findUniqueByHql(hql, code);
    }
}
