package com.tongwii.service;

import com.tongwii.domain.AreaEntity;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
public interface IAreaService {
    /**
     * ����residenceId��ѯarea
     *
     * @param residenceId
     * @return List
     * */
    List<AreaEntity> findAreaByResidenceId(String residenceId);

    /**
     * ����id��ѯarea���¼������һ����¼��
     * @param id
     * @return AreaEntity
     * */
    AreaEntity findById(String id);

    /**
     * ����area��
     * */
    void update(AreaEntity model);
}
