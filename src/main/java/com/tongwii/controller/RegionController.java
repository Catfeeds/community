package com.tongwii.controller;

import com.tongwii.domain.Region;
import com.tongwii.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/9/28.
 */
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    /**
     * 获取所有省份信息
     * @author Yamo
     */
    @GetMapping()
    public ResponseEntity getParentRegion(){
        List<Region> regionEntities = regionService.findByParentCode("0");
        return ResponseEntity.ok(regionEntities);
    }

    /**
     * 根据parentCode查询region信息
     * @author Yamo
     *
     * @param parentCode
     */
    @GetMapping("/{parentCode}")
    public ResponseEntity getParentRegion(@PathVariable String parentCode){
        List<Region> regionEntities = regionService.findByParentCode(parentCode);
        return ResponseEntity.ok(regionEntities);
    }
}
