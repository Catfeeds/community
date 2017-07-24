package com.tongwii.service;

import com.tongwii.po.ResidenceEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IResidenceService {
    /**
     * 根据regionId查询Residence
     *
     * @param regionId
     * @return List
     * */
    List<ResidenceEntity> findResidenceByRegionId(String regionId);

    /**
     * 根据id查询residence表（仅能查询一条）
     * @param id
     * @return ResidenceEntity
     * */
    ResidenceEntity findById(String id);

    /**
     * 更新residence表
     * */
    void update(ResidenceEntity model);
}
