package com.tongwii.service;

import com.tongwii.po.FloorEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IFloorService {
    /**
     * ����areaId��ѯfloor��Ϣ
     * @param areaId
     * @return List
     * */
    List<FloorEntity> findFloorByAreaId(String areaId);

    /**
     * ����id��ѯfloor���¼������һ����¼��
     * @param id
     * @return FloorEntity
     * */
    FloorEntity findById(String id);

    /**
     * ����floor��
     * */
    void update(FloorEntity model);
}
