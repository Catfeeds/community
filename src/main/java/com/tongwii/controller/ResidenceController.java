package com.tongwii.controller;

import com.tongwii.dao.IResidenceDao;
import com.tongwii.domain.Residence;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.ResidenceService;
import com.tongwii.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/api/residence")
public class ResidenceController {
    private final ResidenceService residenceService;
    private final IResidenceDao residenceDao;

    public ResidenceController(ResidenceService residenceService, IResidenceDao residenceDao) {
        this.residenceService = residenceService;
        this.residenceDao = residenceDao;
    }

    /**
     * 添加单个小区信息
     * @author Yamo
     *
     * @param residence
     */
    @PostMapping("/addSingleResidence")
    public ResponseEntity addSingleResidence(@RequestBody Residence residence){
        String userId = SecurityUtils.getCurrentUserId();
        residence.setUserId(userId);
        residenceService.save(residence);
        return ResponseEntity.ok("添加成功!");
    }

    /**
     * 根据residenceId获取社区信息
     * @param residenceId
     */
    @GetMapping("/getResidenceInfo/{residenceId}")
    public ResponseEntity getResidenceInfo(@PathVariable String residenceId){
        Residence residence = residenceService.findById(residenceId);
        return ResponseEntity.ok(residence);
    }
    /**
     * 根据regionId查询residence信息
     * @param regionCode
     * @return result
     * */
    @GetMapping("/find/{regionCode}")
    public ResponseEntity findResidenceByRegionId(@PathVariable String regionCode){

        List<Residence> residenceEntities = residenceService.findResidenceByRegionCode(regionCode);
        if(CollectionUtils.isEmpty(residenceEntities)){
            return ResponseEntity.badRequest().body("抱歉,该区域暂无小区加入本系统!");
        }
        List<Map> jsonArray = new ArrayList<>();
        for(Residence residence : residenceEntities){
            Map<String ,Object> object = new HashMap<>();
            object.put("residenceName", residence.getName());
            object.put("address", residence.getAddress() + residence.getName());
            object.put("residenceId", residence.getId());
            object.put("floorCount", residence.getFloorCount());
            jsonArray.add(object);
        }
        return ResponseEntity.ok(jsonArray);
    }

    /**
     * 修改residence表
     * @param residence
     * @return result
     * */
    @PostMapping("/updateResidenceInfo")
    public ResponseEntity updateResidenceInfo(@RequestBody Residence residence){
        if(residence.getId() == null || residence.getId().isEmpty()){
            return ResponseEntity.badRequest().body("社区实体信息不存在!");
        }
        Residence newResidence = residenceService.findById(residence.getId());
        if(!StringUtils.isEmpty(residence.getName())){
            newResidence.setName(residence.getName());
        }
        if(!StringUtils.isEmpty(residence.getUserId())){
            newResidence.setUserId(residence.getUserId());
        }
        if(!StringUtils.isEmpty(residence.getFloorCount())){
            newResidence.setFloorCount(residence.getFloorCount());
        }
        if(!StringUtils.isEmpty(residence.getRegionCode())){
            newResidence.setRegionCode(residence.getRegionCode());
        }
        if(!StringUtils.isEmpty(residence.getAddress())){
            newResidence.setAddress(residence.getAddress());
        }
        try{
            residenceService.update(newResidence);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("社区信息更新失败!");
        }

        Map<String, Object> object = new HashMap<>();
        object.put("name",newResidence.getName());
        object.put("floorCount",newResidence.getFloorCount());
        object.put("regionId",newResidence.getRegionCode());
        object.put("address",newResidence.getAddress());
        return ResponseEntity.ok(object);
    }

    /**
     * GET  /residence : get all the labels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of residences in body
     */
    @GetMapping("/all")
    public ResponseEntity<List<Residence>> getAllResidences(Pageable pageable) {
        final Page<Residence> page = residenceDao.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/residence/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
