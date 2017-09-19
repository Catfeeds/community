package com.tongwii.service.impl;

import com.tongwii.core.BaseServiceImpl;
import com.tongwii.dao.AreaDao;
import com.tongwii.core.BaseDao;
import com.tongwii.domain.AreaEntity;
import com.tongwii.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<AreaEntity> implements IAreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public BaseDao<AreaEntity, String> getDao() {
        return areaDao;
    }

    @Override
    public List<AreaEntity> findAreaByResidenceId(String residenceId) {
        return areaDao.findAreaByResidenceId(residenceId);
    }

    @Override
    public AreaEntity findById(String id) {
        return super.findById(id);
    }

    @Override
    public void update(AreaEntity model) {
        super.update(model);
    }
}
