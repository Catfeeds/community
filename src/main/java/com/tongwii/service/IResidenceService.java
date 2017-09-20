package com.tongwii.service;

import com.tongwii.domain.ResidenceEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IResidenceService {
    /**
     * Find residence by region id list.
     *
     * @param regionId the region id
     * @return List list
     */
    List<ResidenceEntity> findResidenceByRegionId(String regionId);

    /**
     * Find by id residence entity.
     *
     * @param id the id
     * @return ResidenceEntity residence entity
     */
    ResidenceEntity findById(String id);

    /**
     * Find by code residence entity.
     *
     * @param code the code
     * @return the residence entity
     */
    ResidenceEntity findByCode(String code);

    /**
     * Update.
     *
     * @param model the model
     */
    void update(ResidenceEntity model);
}
