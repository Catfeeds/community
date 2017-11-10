package com.tongwii.service;

import com.tongwii.dao.IRegionDao;
import com.tongwii.domain.Region;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/9/28.
 */
@Service
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

}
