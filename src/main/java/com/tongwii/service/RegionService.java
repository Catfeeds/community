package com.tongwii.service;

import com.tongwii.dao.IRegionDao;
import com.tongwii.domain.RegionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/9/28.
 */
@Service
public class RegionService {
    @Autowired
    private IRegionDao regionDao;

    /**
     * 根据parentCode查询记录
     * @Author Yamo
     *
     * @param parentCode
     */
    public List<RegionEntity> findByParentCode(String parentCode){
        return regionDao.findByParentCode(parentCode);
    }

}
