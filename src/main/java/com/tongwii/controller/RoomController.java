package com.tongwii.controller;

import com.tongwii.constant.UserConstants;
import com.tongwii.domain.Floor;
import com.tongwii.domain.Room;
import com.tongwii.domain.User;
import com.tongwii.domain.UserRoom;
import com.tongwii.service.RoomService;
import com.tongwii.service.UserRoomService;
import com.tongwii.service.UserService;
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
        List<Room> roomEntities = roomService.findByFloorId(floorId);
        List<Map> jsonArray = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roomEntities)) {
            for (Room room : roomEntities) {
                Map<String, Object> object = new HashMap<>();
                object.put("roomCode", room.getRoomCode()+"室");
                object.put("roomStyle", room.getHuXing());
                object.put("roomId", room.getId());
                object.put("unitCode", room.getRoomCode()+"室"+room.getUnitCode()+"单元");
                User user = room.getUserByOwnerId();
                String ownnerName = user.getName();
                String ownnerPhone = user.getPhone();
                Floor floor = room.getFloorByFloorId();
                String address = floor.getResidence().getAddress()+ floor.getCode()+"栋"+room.getUnitCode()+"单元"+room.getRoomCode()+"室";
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
    public ResponseEntity addSingleRoom(@RequestBody Room roomEntity){
        // 首先获取户主的account，看是否存在这个用户
        User user = userService.findByAccount(roomEntity.getOwnerId());
        if(user == null){
            return ResponseEntity.badRequest().body("该户主未在系统注册!");
        }
        List<Room> roomEntities = roomService.findByFloorId(roomEntity.getFloorId());
        boolean exist = true;
        for(Room room: roomEntities){
            if(room.getRoomCode().equals(roomEntity.getRoomCode())){
                exist = false;
            }
        }
        if(exist){
            roomEntity.setOwnerId(user.getId());
            roomService.save(roomEntity);
            UserRoom userRoom = new UserRoom();
            userRoom.setUserId(user.getId());
            userRoom.setType(UserConstants.HUZHU);
            userRoom.setDes("户主");
            userRoom.setRoomId(roomEntity.getId());
            userRoomService.saveSingle(userRoom);
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
    public ResponseEntity updateRoomInfo(@RequestBody Room roomEntity){
        if(roomEntity.getId() == null || roomEntity.getId().isEmpty()){
            return ResponseEntity.badRequest().body("住房实体不存在!");
        }
        Room newRoom = roomService.findById(roomEntity.getId());
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
            return ResponseEntity.badRequest().body("修改的信息关联的数据信息不存在!");
        }
        JSONObject object = new JSONObject();
        object.put("RoomCode", newRoom.getRoomCode());
        object.put("Area", newRoom.getArea());
        object.put("HuXing", newRoom.getHuXing());
        object.put("OwnerId", newRoom.getOwnerId());
        object.put("UnitId", newRoom.getUnitId());
        return ResponseEntity.ok(object);
    }*/

}
