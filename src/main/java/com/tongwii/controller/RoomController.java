package com.tongwii.controller;

import com.tongwii.constant.UserConstants;
import com.tongwii.domain.Floor;
import com.tongwii.domain.Room;
import com.tongwii.domain.User;
import com.tongwii.domain.UserRoom;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.RoomService;
import com.tongwii.service.UserRoomService;
import com.tongwii.service.UserService;
import com.tongwii.util.HeaderUtil;
import com.tongwii.util.PaginationUtil;
import com.tongwii.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by admin on 2017/7/17.
 */
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final Logger log = LoggerFactory.getLogger(RoomController.class);

    private static final String ENTITY_NAME = "room";

    private final RoomService roomService;
    private final UserRoomService userRoomService;
    private final UserService userService;

    public RoomController(RoomService roomService, UserRoomService userRoomService, UserService userService) {this.roomService = roomService;
        this.userRoomService = userRoomService;
        this.userService = userService;
    }

    /**
     * 根据楼宇查询住房信息
     * @param floorId 社区id
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
                User user = room.getOwner();
                String ownnerName = user.getName();
                String ownnerPhone = user.getPhone();
                Floor floor = room.getFloor();
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
     * @param room 房间实体
     */
    @PostMapping("/addSingleRoom")
    public ResponseEntity addSingleRoom(@RequestBody Room room){
        // 首先获取户主的account，看是否存在这个用户
        User user = userService.findByAccount(room.getOwnerId());
        if(user == null){
            return ResponseEntity.badRequest().body("该户主未在系统注册!");
        }
        List<Room> roomEntities = roomService.findByFloorId(room.getFloorId());
        boolean exist = true;
        for(Room room_: roomEntities){
            if(room_.getRoomCode().equals(room_.getRoomCode())){
                exist = false;
            }
        }
        if(exist){
            room.setOwnerId(user.getId());
            roomService.save(room);
            UserRoom userRoom = new UserRoom();
            userRoom.setUserId(user.getId());
            userRoom.setType(UserConstants.HUZHU);
            userRoom.setDes("户主");
            userRoom.setRoomId(room.getId());
            userRoomService.saveSingle(userRoom);
            return ResponseEntity.ok("添加成功!");
        }
        return ResponseEntity.badRequest().body("该住房已存在!");
    }

    /**
     * 修改room表信息
     * @param roomEntity 社区
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

    /**
     * POST  /rooms : Create a new room.
     *
     * @param room the roomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomDTO, or with status 400 (Bad Request) if the room has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) throws URISyntaxException {
        log.debug("REST request to save Room : {}", room);
        if (room.getId() != null) {
            throw new BadRequestAlertException("A new room cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Room result = roomService.save(room);
        return ResponseEntity.created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /rooms : Updates an existing room.
     *
     * @param room the roomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomDTO,
     * or with status 400 (Bad Request) if the roomDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<Room> updateRoom(@RequestBody Room room) throws URISyntaxException {
        log.debug("REST request to update Room : {}", room);
        if (room.getId() == null) {
            return createRoom(room);
        }
        Room result = roomService.save(room);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, room.getId()))
            .body(result);
    }

    /**
     * GET  /rooms : get all the rooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rooms in body
     */
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(Pageable pageable) {
        log.debug("REST request to get a page of Rooms");
        Page<Room> page = roomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rooms/:id : get the "id" room.
     *
     * @param id the id of the roomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable String id) {
        log.debug("REST request to get Room : {}", id);
        Room room = roomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(room));
    }

    /**
     * DELETE  /rooms/:id : delete the "id" room.
     *
     * @param id the id of the roomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        log.debug("REST request to delete Room : {}", id);
        roomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
