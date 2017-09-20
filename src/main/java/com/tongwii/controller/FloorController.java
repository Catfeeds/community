package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.core.Result;
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
    /**
     * 根据aresId查询floor列表
     * @param areaId
     * @return result
     * */
    @RequestMapping(value = "/floor/{areaId}", method = RequestMethod.GET)
    public Result findFloorByAreaId(@PathVariable String areaId){
        if(areaId == null || areaId.isEmpty()){
            return Result.errorResult("楼宇实体信息为空!");
        }
        if(areaService.findById(areaId) == null){
            return Result.errorResult("小区分区实体为空!");
        }
        List<FloorEntity> floorEntities = floorService.findFloorByAreaId(areaId);

        if(floorEntities == null){
            return Result.errorResult("此分区没有楼宇实体!");
        }
        JSONArray jsonArray = new JSONArray();
        for(FloorEntity floorEntity : floorEntities){
            JSONObject object = new JSONObject();
            object.put("floorName", floorEntity.getName());
            object.put("dongCode",floorEntity.getCode());
            object.put("floorUnitId",floorEntity.getParentCode());
            object.put("floorId",floorEntity.getId());
            jsonArray.add(object);
        }

        return Result.successResult(jsonArray);
    }

    /**
     * 修改flooe表信息
     * @param floorEntity
     * @return result
     * */
    @RequestMapping(value = "/updateFloorInfo", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public Result updateFloorInfo(@RequestBody FloorEntity floorEntity){
        if(floorEntity.getId() == null || floorEntity.getId().isEmpty()){
            return Result.errorResult("楼宇实体不存在!");
        }
        FloorEntity newFloor= floorService.findById(floorEntity.getId());
        if(floorEntity.getName() != null && !floorEntity.getName().isEmpty()){
            newFloor.setName(floorEntity.getName());
        }
        if(floorEntity.getCode() != null && !floorEntity.getCode().toString().isEmpty()){
            newFloor.setCode(floorEntity.getCode());
        }
        if(floorEntity.getParentCode() != null && !floorEntity.getParentCode().isEmpty() ){
            newFloor.setParentCode(floorEntity.getParentCode());
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
            return Result.errorResult("楼宇信息修改失败!");
        }
        JSONObject object = new JSONObject();
        object.put("name", newFloor.getName());
        object.put("dongCode", newFloor.getCode());
        object.put("unitCode", newFloor.getParentCode());
        object.put("principalId", newFloor.getPrincipalId());
        object.put("areaId", newFloor.getAreaId());
        return Result.successResult(object);
    }

}
