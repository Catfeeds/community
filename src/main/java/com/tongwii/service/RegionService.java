package com.tongwii.service;

import com.tongwii.dao.IRegionDao;
import com.tongwii.domain.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by admin on 2017/9/28.
 */
@Service
@Transactional
public class RegionService {

    private final IRegionDao regionDao;

    public RegionService(IRegionDao regionDao) {
        this.regionDao = regionDao;
    }

    /**
     * 根据parentCode查询记录
     * @Author Yamo
     *
     * @param parentCode
     */
    public List<Region> findByParentCode(String parentCode){
        return regionDao.findByParentCode(parentCode);
    }

    /**
     * 根据regionCode查询区域信息，如果存在父Code，则追加父name
     *
     * @param regionCode 区域code
     *
     * @return String 整个区域字符串
     */
    public String findByRegionCode(String regionCode) {
        Region region = regionDao.findOneByRegionCode(regionCode);
        StringBuilder regionStr = new StringBuilder();
        while (Objects.nonNull(region)) {
            regionStr.insert(0, region.getName());
            region = regionDao.findOneByRegionCode(region.getParentCode());
        }
        return regionStr.toString();
    }

    /**
     * Save a region.
     *
     * @param region the entity to save
     * @return the persisted entity
     */
    public Region save(Region region) {
        return regionDao.save(region);
    }

    /**
     * Get all the regions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Region> findAll(Pageable pageable) {
        return regionDao.findAll(pageable);
    }

    /**
     * Get one region by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Region findOne(String id) {
        return regionDao.findOne(id);
    }

    /**
     * Delete the region by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        regionDao.delete(id);
    }

}
