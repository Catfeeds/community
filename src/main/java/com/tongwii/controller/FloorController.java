package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.FloorEntity;
import com.tongwii.service.IAreaService;
import com.tongwii.service.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/floor")
public class FloorController {
    @Autowired
    private IFloorService floorService;
    @Autowired
    private IAreaService areaService;
    private TongWIIResult result = new TongWIIResult();
    /**
     * 根据aresId查询floor列表
     * @param areaId
     * @return result
     * */
    @RequestMapping(value = "/floor/{areaId}", method = RequestMethod.GET)
    public TongWIIResult findFloorByAreaId(@PathVariable String areaId){
        if(areaId == null || areaId.isEmpty()){
            result.errorResult("楼宇实体信息为空!");
            return  result;
        }
        if(areaService.findById(areaId) == null){
            result.errorResult("小区分区实体为空!");
            return result;
        }
        List<FloorEntity> floorEntities = floorService.findFloorByAreaId(areaId);

        if(floorEntities == null){
            result.errorResult("此分区没有楼宇实体!");
            return result;
        }
        JSONArray jsonArray = new JSONArray();
        for(FloorEntity floorEntity : floorEntities){
            JSONObject object = new JSONObject();
            object.put("floorName", floorEntity.getName());
            object.put("dongCode",floorEntity.getDongCode());
            object.put("floorUnitId",floorEntity.getUnitCode());
            object.put("floorId",floorEntity.getId());
            jsonArray.add(object);
        }

        result.successResult("楼宇实体信息查询成功!",jsonArray);
        return result;
    }

    /**
     * 修改flooe表信息
     * @param floorEntity
     * @return result
     * */
    @RequestMapping(value = "/updateFloorInfo", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult updateFloorInfo(@RequestBody FloorEntity floorEntity){
        if(floorEntity.getId() == null || floorEntity.getId().isEmpty()){
            result.errorResult("楼宇实体不存在!");
            return result;
        }
        FloorEntity newFloor= floorService.findById(floorEntity.getId());
        if(floorEntity.getName() != null && !floorEntity.getName().isEmpty()){
            newFloor.setName(floorEntity.getName());
        }
        if(floorEntity.getDongCode() != null && !floorEntity.getDongCode().toString().isEmpty()){
            newFloor.setDongCode(floorEntity.getDongCode());
        }
        if(floorEntity.getUnitCode() != null && !floorEntity.getUnitCode().isEmpty() ){
            newFloor.setUnitCode(floorEntity.getUnitCode());
        }
        if(floorEntity.getPrincipalId() != null && !floorEntity.getPrincipalId().isEmpty()){
            newFloor.setPrincipalId(floorEntity.getPrincipalId());
        }
        if(floorEntity.getAreaId() != null && !floorEntity.getAreaId().isEmpty()){
            newFloor.setAreaId(floorEntity.getAreaId());
        }
        try{
            floorService.update(newFloor);
        }catch (Exception e){
            result.errorResult("楼宇信息修改失败!");
            return result;
        }
        JSONObject object = new JSONObject();
        object.put("name", newFloor.getName());
        object.put("dongCode", newFloor.getDongCode());
        object.put("unitCode", newFloor.getUnitCode());
        object.put("principalId", newFloor.getPrincipalId());
        object.put("areaId", newFloor.getAreaId());
        result.successResult("修改楼宇实体成功!", object);
        return result;
    }

}
