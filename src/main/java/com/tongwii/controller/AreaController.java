package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.domain.AreaEntity;
import com.tongwii.service.IAreaService;
import com.tongwii.service.IResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private IAreaService areaService;
    @Autowired
    private IResidenceService residenceService;
    private TongWIIResult result = new TongWIIResult();
    /**
     * 根据residenceId查询area表
     * @param residenceId
     * @return result
     * */
    @RequestMapping(value = "/{residenceId}", method = RequestMethod.GET)
    public TongWIIResult findAreaByResidenceId(@PathVariable String residenceId){
        if(residenceId == null || residenceId.isEmpty()){
            result.errorResult("社区实体为空!");
            return  result;
        }
        if(residenceService.findById(residenceId) == null){
            result.errorResult("社区实体不存在!");
            return  result;
        }
        List<AreaEntity> areaEntities = areaService.findAreaByResidenceId(residenceId);

        if(areaEntities == null){
            result.errorResult("社区下无分区!");
            return result;
        }
        JSONArray jsonArray = new JSONArray();
        for(AreaEntity areaEntity1 : areaEntities){
            JSONObject object = new JSONObject();
            object.put("areaName", areaEntity1.getName());
            object.put("areaId",areaEntity1.getId());
            jsonArray.add(object);
        }

        result.successResult("分区信息查询成功!",jsonArray);
        return result;
    }
    /**
     * 修改area表
     * @param areaEntity
     * @return result
     */
    @RequestMapping(value = "/updateAreaInfo", method = RequestMethod.POST)
    public TongWIIResult updateAreaInfo(@RequestBody AreaEntity areaEntity){
        if(areaEntity.getId() == null || areaEntity.getId().isEmpty()){
            result.errorResult("小区分区实体为空!");
            return result;
        }
        AreaEntity newArea = areaService.findById(areaEntity.getId());
        if(areaEntity.getName() != null && !areaEntity.getName().isEmpty()){
            newArea.setName(areaEntity.getName());
        }
        if(areaEntity.getBuildDate() != null && !areaEntity.getBuildDate().toString().isEmpty()){
            newArea.setBuildDate(areaEntity.getBuildDate());
        }
        if(areaEntity.getResidenceId() != null && !areaEntity.getResidenceId().isEmpty() ){
            newArea.setResidenceId(areaEntity.getResidenceId());
        }
        if(areaEntity.getChargeId() != null && !areaEntity.getChargeId().isEmpty()){
            newArea.setChargeId(areaEntity.getChargeId());
        }
        try{
            areaService.update(newArea);
        }catch (Exception e){
            result.errorResult("分区信息更新失败");
        }

        JSONObject object = new JSONObject();
        object.put("name", newArea.getName());
        object.put("buildDate", newArea.getBuildDate());
        object.put("residenceId", newArea.getResidenceId());
        object.put("chargeId", newArea.getChargeId());
        result.successResult("分区信息修改成功!", object);
        return result;
    }

}
