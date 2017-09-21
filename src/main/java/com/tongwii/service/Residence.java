package com.tongwii.service;

import com.tongwii.dao.IResidenceDao;
import com.tongwii.domain.ResidenceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
@Transactional
public class Residence {
    private final IResidenceDao residenceDao;

    public Residence(IResidenceDao residenceDao) {
        this.residenceDao = residenceDao;
    }

    public List<ResidenceEntity> findResidenceByRegionId(String regionId) {
        return residenceDao.findByRegionId(regionId);
    }

    public ResidenceEntity findByCode(String code) {
        return residenceDao.findByCode(code);
    }

    public ResidenceEntity findById(String id) {
        return residenceDao.findOne(id);
    }

    public void update(ResidenceEntity model) {
        residenceDao.save(model);
    }
}
