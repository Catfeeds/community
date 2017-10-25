package com.tongwii.controller;

import com.tongwii.constant.Constants;
import com.tongwii.domain.FloorEntity;
import com.tongwii.domain.RoomEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoomEntity;
import com.tongwii.service.RoomService;
import com.tongwii.service.UserRoomService;
import com.tongwii.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserRoomService userRoomService;
    @Autowired
    private UserService userService;
    /**
     * 根据楼宇查询住房信息
     * @param floorId
     * @return result
     * */
    @GetMapping(value = "/selectRoomByFloor/{floorId}")
    public ResponseEntity selectRoomByFloor(@PathVariable String floorId){
        if(StringUtils.isEmpty(floorId)){
            return ResponseEntity.badRequest().body("楼宇未选择!");
        }
        List<RoomEntity> roomEntities = roomService.findByFloorId(floorId);
        JSONArray jsonArray = new JSONArray();
        if(!CollectionUtils.isEmpty(roomEntities)) {
            for (RoomEntity room : roomEntities) {
                JSONObject object = new JSONObject();
                object.put("roomCode", room.getRoomCode()+"室");
                object.put("roomStyle", room.getHuXing());
                object.put("roomId", room.getId());
                object.put("unitCode", room.getRoomCode()+"室"+room.getUnitCode()+"单元");
                UserEntity userEntity = room.getUserByOwnerId();
                String ownnerName = userEntity.getName();
                String ownnerPhone = userEntity.getPhone();
                FloorEntity floorEntity = room.getFloorByFloorId();
                String address = floorEntity.getResidenceEntity().getAddress()+floorEntity.getCode()+"栋"+room.getUnitCode()+"单元"+room.getRoomCode()+"室";
                System.out.println(address);
                object.put("ownerName", ownnerName);
                object.put("ownerPhone", ownnerPhone);
                object.put("address", address);
                jsonArray.add(object);
            }
        }
        return ResponseEntity.ok(jsonArray);
    }

    /**
     * 添加room信息
     * @author Yamo
     * @param roomEntity
     */
    @PostMapping("/addSingleRoom")
    public ResponseEntity addSingleRoom(@RequestBody RoomEntity roomEntity){
        // 首先获取户主的account，看是否存在这个用户
        UserEntity userEntity = userService.findByAccount(roomEntity.getOwnerId());
        if(userEntity == null){
            return ResponseEntity.badRequest().body("该户主未在系统注册!");
        }
        List<RoomEntity> roomEntities = roomService.findByFloorId(roomEntity.getFloorId());
        boolean exist = true;
        for(RoomEntity room: roomEntities){
            if(room.getRoomCode().equals(roomEntity.getRoomCode())){
                exist = false;
            }
        }
        if(exist){
            roomEntity.setOwnerId(userEntity.getId());
            roomService.save(roomEntity);
            UserRoomEntity userRoomEntity = new UserRoomEntity();
            userRoomEntity.setUserId(userEntity.getId());
            userRoomEntity.setType(Constants.HUZHU);
            userRoomEntity.setDes("户主");
            userRoomEntity.setRoomId(roomEntity.getId());
            userRoomService.saveSingle(userRoomEntity);
            return ResponseEntity.ok("添加成功!");
        }
        return ResponseEntity.badRequest().body("该住房已存在!");
    }
    /**
     * 修改room表信息
     * @param roomEntity
     * @return result
     * */
    /*
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
    }*/

}
