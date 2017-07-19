package com.tongwii.service.impl;

import com.tongwii.dao.BaseDao;
import com.tongwii.dao.FloorDao;
import com.tongwii.po.FloorEntity;
import com.tongwii.service.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@Service
public class FloorService extends BaseServiceImpl<FloorEntity> implements IFloorService{
    @Autowired
    private FloorDao floorDao;
    @Override
    public BaseDao<FloorEntity, String> getDao() {
        return floorDao;
    }

    @Override
    public List<FloorEntity> findFloorByAreaId(String areaId) {
        return floorDao.findFloorByAreId(areaId);
    }

    @Override
    public void update(FloorEntity model) {
        super.update(model);
    }
}
