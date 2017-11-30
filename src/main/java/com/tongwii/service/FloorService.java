package com.tongwii.service;

import com.tongwii.dao.IFloorDao;
import com.tongwii.domain.Floor;
import com.tongwii.dto.FloorDTO;
import com.tongwii.dto.mapper.FloorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final Logger log = LoggerFactory.getLogger(FloorService.class);

    private final IFloorDao floorDao;
    private final FloorMapper floorMapper;

    public FloorService(IFloorDao floorDao, FloorMapper floorMapper) {
        this.floorDao = floorDao;
        this.floorMapper = floorMapper;
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

    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save
     * @return the persisted entity
     */
    public FloorDTO save(FloorDTO floorDTO) {
        log.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorDao.save(floor);
        return floorMapper.toDto(floor);
    }

    /**
     * Get all the floors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FloorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Floors");
        return floorDao.findAll(pageable)
            .map(floorMapper::toDto);
    }

    /**
     * Get one floor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FloorDTO findOne(String id) {
        log.debug("Request to get Floor : {}", id);
        Floor floor = floorDao.findOne(id);
        return floorMapper.toDto(floor);
    }

    /**
     * Delete the floor by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Floor : {}", id);
        floorDao.delete(id);
    }
}
