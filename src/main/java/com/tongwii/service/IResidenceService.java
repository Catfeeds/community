package com.tongwii.service;

import com.tongwii.po.ResidenceEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface IResidenceService {
    /**
     * ����regionId��ѯResidence
     *
     * @param regionId
     * @return List
     * */
    List<ResidenceEntity> findResidenceByRegionId(String regionId);

    /**
     * ����id��ѯresidence�����ܲ�ѯһ����
     * @param id
     * @return ResidenceEntity
     * */
    ResidenceEntity findById(String id);

    /**
     * ����С�����֤��ѯ��¼
     * @param code
     * */
    ResidenceEntity findByCode(String code);

    /**
     * ����residence��
     * */
    void update(ResidenceEntity model);
}
