package com.tongwii.service.impl;

import com.tongwii.dao.BaseDao;
import com.tongwii.dao.ResidenceDao;
import com.tongwii.po.ResidenceEntity;
import com.tongwii.service.IResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
public class ResidenceService extends BaseServiceImpl<ResidenceEntity> implements IResidenceService {
    @Autowired
    private ResidenceDao residenceDao;

    @Override
    public BaseDao<ResidenceEntity, String> getDao() {
        return residenceDao;
    }

    @Override
    public List<ResidenceEntity> findResidenceByRegionId(String regionId) {
        return residenceDao.findResidenceByRegionId(regionId);
    }

    @Override
    public ResidenceEntity findById(String id) {
        return residenceDao.findById(id);
    }

    @Override
    public void update(ResidenceEntity model) {
        super.update(model);
    }
}
