package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.core.Result;
import com.tongwii.domain.ResidenceEntity;
import com.tongwii.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
     * 根据regionId查询residence信息
     * @param regionId
     * @return result
     * */
    @RequestMapping(value = "/find/{regionId}", method = RequestMethod.GET)
    public Result findResidenceByRegionId(@PathVariable String regionId){
        if(regionId == null || regionId.isEmpty()){
            return Result.errorResult("区域信息为空!");
        }

        List<ResidenceEntity> residenceEntities = residenceService.findResidenceByRegionId(regionId);
        if(CollectionUtils.isEmpty(residenceEntities)){
            return Result.errorResult("社区实体不存在!");
        }
        JSONArray jsonArray = new JSONArray();
        for(ResidenceEntity residenceEntity : residenceEntities){
            JSONObject object = new JSONObject();
            object.put("residenceName", residenceEntity.getName());
            object.put("residenceUrl",residenceEntity.getServerUrl());
            object.put("residenceId",residenceEntity.getId());
            object.put("floorCount", residenceEntity.getFloorCount());
            jsonArray.add(object);
        }
        return Result.successResult(jsonArray);
    }

    /**
     * 修改residence表
     * @param residenceEntity
     * @return result
     * */
    @RequestMapping(value = "/updateResidenceInfo", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public Result updateResidenceInfo(@RequestBody ResidenceEntity residenceEntity){
        if(residenceEntity.getId() == null || residenceEntity.getId().isEmpty()){
            return Result.errorResult("社区实体信息不存在!");
        }
        ResidenceEntity newResidence = residenceService.findById(residenceEntity.getId());
        if(residenceEntity.getName() != null && !residenceEntity.getName().isEmpty()){
            newResidence.setName(residenceEntity.getName());
        }
        if(residenceEntity.getUserId() != null && !residenceEntity.getUserId().toString().isEmpty()){
            newResidence.setUserId(residenceEntity.getUserId());
        }
        if(residenceEntity.getFloorCount() != null && !residenceEntity.getFloorCount().toString().isEmpty() ){
            newResidence.setFloorCount(residenceEntity.getFloorCount());
        }
        if(residenceEntity.getRegionId() != null && !residenceEntity.getRegionId().isEmpty()){
            newResidence.setRegionId(residenceEntity.getRegionId());
        }
        if(residenceEntity.getServerUrl() != null && !residenceEntity.getServerUrl().isEmpty()){
            newResidence.setServerUrl(residenceEntity.getServerUrl());
        }
        try{
            residenceService.update(newResidence);
        }catch (Exception e){
            return Result.errorResult("社区信息更新失败!");
        }

        JSONObject object = new JSONObject();
        object.put("name",newResidence.getName());
        object.put("floorCount",newResidence.getFloorCount());
        object.put("regionId",newResidence.getRegionId());
        object.put("serverUrl",newResidence.getServerUrl());
        return Result.successResult(object);
    }

}
