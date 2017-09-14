package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.ResidenceEntity;
import com.tongwii.service.IResidenceService;
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
    private IResidenceService residenceService;
//    @Autowired
//    private IR
    private TongWIIResult result = new TongWIIResult();

    /**
     * 根据regionId查询residence信息
     * @param regionId
     * @return result
     * */
    @RequestMapping(value = "/residence/{regionId}", method = RequestMethod.GET)
    public TongWIIResult findResidenceByRegionId(@PathVariable String regionId){
        if(regionId == null || regionId.isEmpty()){
            result.errorResult("区域信息为空!");
            return result;
        }

        List<ResidenceEntity> residenceEntities = residenceService.findResidenceByRegionId(regionId);
        if(CollectionUtils.isEmpty(residenceEntities)){
            result.errorResult("社区实体不存在!");
            return result;
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
        result.successResult("社区实体信息查询成功!",jsonArray);
        return result;
    }

    /**
     * 修改residence表
     * @param residenceEntity
     * @return result
     * */
    @RequestMapping(value = "/updateResidenceInfo", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult updateResidenceInfo(@RequestBody ResidenceEntity residenceEntity){
        if(residenceEntity.getId() == null || residenceEntity.getId().isEmpty()){
            result.errorResult("社区实体信息不存在!");
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
            result.errorResult("社区信息更新失败!");
            return result;
        }

        JSONObject object = new JSONObject();
        object.put("name",newResidence.getName());
        object.put("floorCount",newResidence.getFloorCount());
        object.put("regionId",newResidence.getRegionId());
        object.put("serverUrl",newResidence.getServerUrl());
        result.successResult("修改社区信息成功!",object);
        return result;
    }

}
