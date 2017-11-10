package com.tongwii.service;

import com.tongwii.dao.IResidenceDao;
import com.tongwii.domain.Residence;
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
    private final IResidenceDao residenceDao;

    public ResidenceService(IResidenceDao residenceDao) {
        this.residenceDao = residenceDao;
    }

    public List<Residence> findResidenceByRegionCode(String regionId) {
        return residenceDao.findByRegionCode(regionId);
    }

    public Residence findById(String id) {
        return residenceDao.findOne(id);
    }

    public void update(Residence newResidence) {
        residenceDao.save(newResidence);
    }

    public Residence save(Residence residence) {
        return residenceDao.save(residence);
    }
}
