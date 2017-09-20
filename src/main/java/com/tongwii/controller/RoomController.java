package com.tongwii.controller;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.core.Result;
import com.tongwii.po.RoomEntity;
import com.tongwii.service.IRoomService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private IRoomService roomService;
    /**
     * 根据单元楼id查询住房信息
     * @param roomEntity
     * @return result
     * */
    @RequestMapping(value = "/selectRoomByUnit", method = RequestMethod.POST)
    public Result selectRoomByUnit(@RequestBody RoomEntity roomEntity){
        if(roomEntity.getUnitId() == null || roomEntity.getUnitId().isEmpty()){
            return Result.errorResult("单元编号为空!");
        }
        double area = roomEntity.getArea()/1.0;
        int areaId = (int)area;
        List<RoomEntity> roomEntities = roomService.findRoomForChose(roomEntity.getUnitId(), Integer.toString(areaId));
        JSONArray jsonArray = new JSONArray();
        if(!CollectionUtils.isEmpty(roomEntities)) {
            for (RoomEntity room : roomEntities) {
                JSONObject object = new JSONObject();
                object.put("roomCode", room.getRoomCode());
                object.put("roomId", room.getId());
                jsonArray.add(object);
            }
        }
        return Result.successResult(jsonArray);
    }

    /**
     * 修改room表信息
     * @param roomEntity
     * @return result
     * */
    @RequestMapping(value = "/updateRoomInfo", method = RequestMethod.POST )
    public Result updateRoomInfo(@RequestBody RoomEntity roomEntity){
        if(roomEntity.getId() == null || roomEntity.getId().isEmpty()){
            return Result.errorResult("住房实体不存在!");
        }
        RoomEntity newRoom = roomService.findById(roomEntity.getId());
        if(roomEntity.getRoomCode()!=null && !roomEntity.getRoomCode().isEmpty()){
            newRoom.setRoomCode(roomEntity.getRoomCode());
        }
        if(roomEntity.getArea() != null && !roomEntity.getArea().toString().isEmpty()){
            newRoom.setArea(roomEntity.getArea());
        }
        if(roomEntity.getHuXing() != null && !roomEntity.getHuXing().toString().isEmpty()){
            newRoom.setHuXing(roomEntity.getHuXing());
        }
        if(roomEntity.getOwnerId() != null && !roomEntity.getOwnerId().isEmpty() ){
            newRoom.setOwnerId(roomEntity.getOwnerId());
        }
        if(roomEntity.getUnitId() != null && !roomEntity.getUnitId().isEmpty()){
            newRoom.setUnitId(roomEntity.getUnitId());
        }
        try{
            roomService.update(newRoom);
        }catch (Exception e){
            return Result.errorResult("修改的信息关联的数据信息不存在!");
        }
        JSONObject object = new JSONObject();
        object.put("RoomCode", newRoom.getRoomCode());
        object.put("Area", newRoom.getArea());
        object.put("HuXing", newRoom.getHuXing());
        object.put("OwnerId", newRoom.getOwnerId());
        object.put("UnitId", newRoom.getUnitId());
        return Result.successResult(object);
    }

}
