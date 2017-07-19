package com.tongwii.service;

import com.tongwii.po.FloorEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IFloorService {
    /**
     * 根据areaId查询floor信息
     * @param areaId
     * @return List
     * */
    List<FloorEntity> findFloorByAreaId(String areaId);

    /**
     * 根据id查询floor表记录（仅有一条记录）
     * @param id
     * @return FloorEntity
     * */
    FloorEntity findById(String id);

    /**
     * 更新floor表
     * */
    void update(FloorEntity model);
}
