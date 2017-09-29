package com.tongwii.controller;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;
import com.tongwii.core.Result;
import com.tongwii.domain.FloorEntity;
import com.tongwii.service.FloorService;
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
@RequestMapping("/floor")
public class FloorController {
    @Autowired
    private FloorService floorService;
    @Autowired
    private ResidenceService residenceService;
    /**
     * 添加单个楼宇信息
     * @author Yamo
     *
     * @param floorEntity
     */
    @PostMapping("/addSingleFloor")
    public ResponseEntity addSingleFloor(@RequestBody FloorEntity floorEntity){
        // TODO 此处需要做楼宇名称的唯一性设置
        try {
            floorService.save(floorEntity);
            return ResponseEntity.ok("添加成功!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("添加失败!");
        }
    }

    /**
     * 根据floorCode查询记录
     * @author Yamo
     * @param floorCode
     * @param residenceId
     */
    @GetMapping("/getFloorByFloorCode/{residenceId}/{floorCode}")
    public ResponseEntity getFloorByFloorCode(@PathVariable String residenceId, @PathVariable String floorCode){
        List<FloorEntity> floorEntities = floorService.findByCodeAndResidenceId(residenceId, floorCode);
        if(CollectionUtils.isEmpty(floorEntities)){
            return ResponseEntity.badRequest().body("无楼宇信息!");
        }
        return ResponseEntity.ok(floorEntities);
    }
    /**
     * 根据residenceId查询floor列表
     * @param residenceId
     * @return result
     * */
    @GetMapping(value = "/floor/{residenceId}")
    public Result findFloorByResidenceId(@PathVariable String residenceId){
        List<FloorEntity> floorEntities = floorService.findFloorByResidenceId(residenceId);
        String address = residenceService.findById(residenceId).getAddress();
        if(floorEntities == null){
            return Result.errorResult("此社区没有楼宇实体!");
        }
        JSONArray jsonArray = new JSONArray();
        for(FloorEntity floorEntity : floorEntities){
            JSONObject object = new JSONObject();
            object.put("floorName",floorEntity.getCode());
            object.put("floorPiles", floorEntity.getFloorPiles());
            object.put("isElev", floorEntity.getElev());
            object.put("floorId",floorEntity.getId());
            object.put("address", address+floorEntity.getCode()+"栋");
            jsonArray.add(object);
        }
        return Result.successResult(jsonArray);
    }

    /**
     * 修改flooe表信息
     * @param floorEntity
     * @return result
     * */
    @PutMapping(value = "/updateFloorInfo")
    public Result updateFloorInfo(@RequestBody FloorEntity floorEntity){
        if(StringUtils.isEmpty(floorEntity.getId())){
            return Result.errorResult("楼宇实体不存在!");
        }
        FloorEntity newFloor= floorService.findById(floorEntity.getId());
        if(!StringUtils.isEmpty(floorEntity.getCode())){
            newFloor.setCode(floorEntity.getCode());
        }
        if(!StringUtils.isEmpty(floorEntity.getPrincipalId())){
            newFloor.setPrincipalId(floorEntity.getPrincipalId());
        }
        if(!StringUtils.isEmpty(floorEntity.getResidenceId())){
            newFloor.setResidenceId(floorEntity.getResidenceId());
        }
        try{
            floorService.update(newFloor);
        }catch (Exception e){
            return Result.errorResult("楼宇信息修改失败!");
        }
        JSONObject object = new JSONObject();
        object.put("floorName", newFloor.getCode());
        object.put("principalId", newFloor.getPrincipalId());
        object.put("residenceId", newFloor.getResidenceId());
        return Result.successResult(object);
    }

}
