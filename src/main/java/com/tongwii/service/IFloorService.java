package com.tongwii.service;

import com.tongwii.domain.FloorEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */
public interface IFloorService {
    /**
     * 根据areaId查询floor信息
     *
     * @param areaId the area id
     * @return List list
     */
    List<FloorEntity> findFloorByAreaId(String areaId);

    /**
     * 根据id查询floor表记录（仅有一条记录）
     *
     * @param id the id
     * @return FloorEntity floor entity
     */
    FloorEntity findById(String id);

    /**
     * 更新floor表
     *
     * @param model the model
     */
    void update(FloorEntity model);

    /**
     * 根据id查询楼宇的栋和单元信息
     *
     * @param id the id
     * @return the list 该MAP包含栋信息和单元信息
     */
    Map<String, FloorEntity> findFloorById(String id);
}
