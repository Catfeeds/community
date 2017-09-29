package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.core.Result;
import com.tongwii.domain.ResidenceEntity;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/residence")
public class ResidenceController {
    @Autowired
    private ResidenceService residenceService;
    /**
     * 添加单个小区信息
     * @author Yamo
     *
     * @param residenceEntity
     */
    @PostMapping("/addSingleResidence")
    public ResponseEntity addSingleResidence(@RequestBody ResidenceEntity residenceEntity){
        String userId = SecurityUtils.getCurrentUserId();
        residenceEntity.setUserId(userId);
        residenceService.save(residenceEntity);
        return ResponseEntity.ok("添加成功!");
    }

    /**
     * 根据regionId查询residence信息
     * @param regionCode
     * @return result
     * */
    @GetMapping("/find/{regionCode}")
    public ResponseEntity findResidenceByRegionId(@PathVariable String regionCode){

        List<ResidenceEntity> residenceEntities = residenceService.findResidenceByRegionCode(regionCode);
        if(CollectionUtils.isEmpty(residenceEntities)){
            return ResponseEntity.badRequest().body("社区实体不存在!");
        }
        JSONArray jsonArray = new JSONArray();
        for(ResidenceEntity residenceEntity : residenceEntities){
            JSONObject object = new JSONObject();
            object.put("residenceName", residenceEntity.getName());
            object.put("address",residenceEntity.getAddress());
            object.put("residenceId",residenceEntity.getId());
            object.put("floorCount", residenceEntity.getFloorCount());
            object.put("address", residenceEntity.getAddress());
            jsonArray.add(object);
        }
        return ResponseEntity.ok(jsonArray);
    }

    /**
     * 修改residence表
     * @param residenceEntity
     * @return result
     * */
    @PostMapping("/updateResidenceInfo")
    public Result updateResidenceInfo(@RequestBody ResidenceEntity residenceEntity){
        if(residenceEntity.getId() == null || residenceEntity.getId().isEmpty()){
            return Result.errorResult("社区实体信息不存在!");
        }
        ResidenceEntity newResidence = residenceService.findById(residenceEntity.getId());
        if(!StringUtils.isEmpty(residenceEntity.getName())){
            newResidence.setName(residenceEntity.getName());
        }
        if(!StringUtils.isEmpty(residenceEntity.getUserId())){
            newResidence.setUserId(residenceEntity.getUserId());
        }
        if(!StringUtils.isEmpty(residenceEntity.getFloorCount())){
            newResidence.setFloorCount(residenceEntity.getFloorCount());
        }
        if(!StringUtils.isEmpty(residenceEntity.getRegionCode())){
            newResidence.setRegionCode(residenceEntity.getRegionCode());
        }
        if(!StringUtils.isEmpty(residenceEntity.getAddress())){
            newResidence.setAddress(residenceEntity.getAddress());
        }
        try{
            residenceService.update(newResidence);
        }catch (Exception e){
            return Result.errorResult("社区信息更新失败!");
        }


        JSONObject object = new JSONObject();
        object.put("name",newResidence.getName());
        object.put("floorCount",newResidence.getFloorCount());
        object.put("regionId",newResidence.getRegionCode());
        object.put("address",newResidence.getAddress());
        return Result.successResult(object);
    }

}
