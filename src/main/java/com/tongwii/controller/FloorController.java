package com.tongwii.controller;

import com.tongwii.domain.Floor;
import com.tongwii.service.FloorService;
import com.tongwii.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param floor
     */
    @PostMapping("/addSingleFloor")
    public ResponseEntity addSingleFloor(@RequestBody Floor floor){
        // TODO 此处需要做楼宇名称的唯一性设置
        List<Floor> floorEntities = floorService.findFloorByResidenceId(floor.getResidenceId());
        boolean floorExist = false;
        if(!CollectionUtils.isEmpty(floorEntities)){
            for(Floor floor1 : floorEntities){
                if(floor1.getCode().equals(floor.getCode())){
                    floorExist = true;
                }
            }
        }
        if(floorExist){
            return ResponseEntity.badRequest().body("楼宇已存在!");
        }
        try {
            floorService.save(floor);
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
        List<Floor> floorEntities = floorService.findByCodeAndResidenceId(residenceId, floorCode);
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
    public ResponseEntity findFloorByResidenceId(@PathVariable String residenceId){
        List<Floor> floorEntities = floorService.findFloorByResidenceId(residenceId);
        String address = residenceService.findById(residenceId).getAddress();
        if(floorEntities == null){
            return ResponseEntity.badRequest().body("此社区没有楼宇实体!");
        }
        List<Map> jsonArray = new ArrayList<>();
        for(Floor floor : floorEntities){
            Map<String, Object> object = new HashMap<>();
            object.put("floorName", floor.getCode());
            object.put("floorPiles", floor.getFloorPiles());
            object.put("isElev", floor.getElev());
            object.put("floorId", floor.getId());
            object.put("address", address+ floor.getCode()+"栋");
            jsonArray.add(object);
        }
        return ResponseEntity.ok(jsonArray);
    }

    /**
     * 修改flooe表信息
     * @param floor
     * @return result
     * */
    @PutMapping(value = "/updateFloorInfo")
    public ResponseEntity updateFloorInfo(@RequestBody Floor floor){
        if(StringUtils.isEmpty(floor.getId())){
            return ResponseEntity.badRequest().body("楼宇实体不存在!");
        }
        Floor newFloor= floorService.findById(floor.getId());
        if(!StringUtils.isEmpty(floor.getCode())){
            newFloor.setCode(floor.getCode());
        }
        if(!StringUtils.isEmpty(floor.getPrincipalId())){
            newFloor.setPrincipalId(floor.getPrincipalId());
        }
        if(!StringUtils.isEmpty(floor.getResidenceId())){
            newFloor.setResidenceId(floor.getResidenceId());
        }
        try{
            floorService.update(newFloor);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("楼宇信息修改失败!");
        }
        Map<String, Object> object = new HashMap<>();
        object.put("floorName", newFloor.getCode());
        object.put("principalId", newFloor.getPrincipalId());
        object.put("residenceId", newFloor.getResidenceId());
        return ResponseEntity.ok(object);
    }

}
