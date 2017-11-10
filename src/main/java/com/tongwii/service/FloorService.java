package com.tongwii.service;

import com.tongwii.dao.IFloorDao;
import com.tongwii.domain.Floor;
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

    public List<Floor> findFloorByResidenceId(String residenceId) {
        return floorDao.findByResidenceId(residenceId);
    }

    public void update(Floor model) {
        floorDao.save(model);
    }

    public Map<String, Floor> findFloorById(String id) {
        Map<String, Floor> floorMap = new HashMap<>();
        Floor unitEntity = floorDao.findOne(id);
        if (Objects.nonNull(unitEntity)) {
            floorMap.put(Floor.UNIT, unitEntity);
        }
        return floorMap;
    }

    public Floor findById(String id) {
        return floorDao.findOne(id);
    }

    public Floor save(Floor floor){
        return floorDao.save(floor);
    }

    public List<Floor> findByCodeAndResidenceId(String risidenceId, String floorCode){
        return floorDao.findByCodeAndResidenceId(floorCode, risidenceId);
    }
}
