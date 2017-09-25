package com.tongwii.service;

import com.tongwii.dao.IResidenceDao;
import com.tongwii.domain.ResidenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Service
@Transactional
public class ResidenceService {
    @Autowired
    private IResidenceDao residenceDao;

    public List<ResidenceEntity> findResidenceByRegionId(String regionId) {
        return residenceDao.findByRegionId(regionId);
    }

    public ResidenceEntity findById(String id) {
        return residenceDao.findOne(id);
    }

    public void update(ResidenceEntity newResidence) {
         residenceDao.save(newResidence);
    }
}
