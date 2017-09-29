package com.tongwii.service;

import com.tongwii.dao.IFloorDao;
import com.tongwii.domain.FloorEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by admin on 2017/7/18.
 */
@Service
@Transactional
public class FloorService {
    private final IFloorDao floorDao;

    public FloorService(IFloorDao floorDao) {
        this.floorDao = floorDao;
    }

    public List<FloorEntity> findFloorByResidenceId(String residenceId) {
        return floorDao.findByResidenceId(residenceId);
    }

    public void update(FloorEntity model) {
        floorDao.save(model);
    }

    public Map<String, FloorEntity> findFloorById(String id) {
        Map<String, FloorEntity> floorMap = new HashMap<>();
        FloorEntity unitEntity = floorDao.findOne(id);
        if (Objects.nonNull(unitEntity)) {
            floorMap.put(FloorEntity.UNIT, unitEntity);
        }
        return floorMap;
    }

    public FloorEntity findById(String id) {
        return floorDao.findOne(id);
    }

    public FloorEntity save(FloorEntity floorEntity){
        return floorDao.save(floorEntity);
    }

    public List<FloorEntity> findByCodeAndResidenceId(String risidenceId, String floorCode){
        return floorDao.findByCodeAndResidenceId(floorCode, risidenceId);
    }
}
