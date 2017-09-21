package com.tongwii.service;

import com.tongwii.dao.IAreaDao;
import com.tongwii.domain.AreaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
@Transactional
public class AreaService {
    private final IAreaDao areaDao;

    public AreaService(IAreaDao areaDao) {
        this.areaDao = areaDao;
    }

    public List<AreaEntity> findAreaByResidenceId(String residenceId) {
        return areaDao.findByResidenceId(residenceId);
    }

    public AreaEntity findById(String id) {
        return areaDao.findOne(id);
    }

    public void update(AreaEntity model) {
        areaDao.save(model);
    }
}
