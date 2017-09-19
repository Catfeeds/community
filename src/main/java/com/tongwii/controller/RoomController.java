package com.tongwii.controller;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.domain.RoomEntity;
import com.tongwii.service.IRoomService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private IRoomService roomService;
    private TongWIIResult result = new TongWIIResult();

    /**
     * 根据单元楼id查询住房信息
     * @param roomEntity
     * @return result
     * */
    @RequestMapping(value = "/selectRoomByUnit", method = RequestMethod.POST)
    public TongWIIResult selectRoomByUnit(@RequestBody RoomEntity roomEntity){
        if(roomEntity.getUnitId() == null || roomEntity.getUnitId().isEmpty()){
            result.errorResult("单元编号为空!");
            return result;
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
        result.successResult("房间信息查询成功!", jsonArray);
        return result;
    }

    /**
     * 修改room表信息
     * @param roomEntity
     * @return result
     * */
    @RequestMapping(value = "/updateRoomInfo", method = RequestMethod.POST )
    public TongWIIResult updateRoomInfo(@RequestBody RoomEntity roomEntity){
        if(roomEntity.getId() == null || roomEntity.getId().isEmpty()){
            result.errorResult("住房实体不存在!");
            return result;
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
            result.errorResult("修改的信息关联的数据信息不存在!");
            return result;
        }

        JSONObject object = new JSONObject();
        object.put("RoomCode", newRoom.getRoomCode());
        object.put("Area", newRoom.getArea());
        object.put("HuXing", newRoom.getHuXing());
        object.put("OwnerId", newRoom.getOwnerId());
        object.put("UnitId", newRoom.getUnitId());
        result.successResult("住房信息修改成功!", object);
        return result;
    }

}
