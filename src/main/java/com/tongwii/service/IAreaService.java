package com.tongwii.service;

import com.tongwii.po.AreaEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
public interface IAreaService {
    /**
     * 根据residenceId查询area
     *
     * @param residenceId
     * @return List
     * */
    List<AreaEntity> findAreaByResidenceId(String residenceId);

    /**
     * 根据id查询area表记录（仅有一条记录）
     * @param id
     * @return AreaEntity
     * */
    AreaEntity findById(String id);

    /**
     * 更新area表
     * */
    void update(AreaEntity model);
}
