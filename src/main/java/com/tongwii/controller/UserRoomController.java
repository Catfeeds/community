package com.tongwii.controller;

import com.tongwii.domain.User;
import com.tongwii.domain.UserRoom;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.service.UserRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/30.
 */
@RestController
@RequestMapping("/api/userRoom")
public class UserRoomController {

    private final UserRoomService userRoomService;
    private final UserMapper userMapper;

    public UserRoomController(UserRoomService userRoomService, UserMapper userMapper) {
        this.userRoomService = userRoomService;
        this.userMapper = userMapper;
    }

    /**
     * 根据roomId查询用户列表
     * @author Yamo
     * @param roomId
     */
    @GetMapping("/getUserByRoomId/{roomId}")
    public ResponseEntity getUserByRoomId(@PathVariable String roomId){
        List<UserRoom> userRoomEntities = userRoomService.findUsersByRoomId(roomId);
        if(CollectionUtils.isEmpty(userRoomEntities)){
            return ResponseEntity.badRequest().body("该住房暂未出售，无住户!");
        }
        List<User> userEntities = new ArrayList<>();
        for(UserRoom userRoom : userRoomEntities){
            userEntities.add(userRoom.getUserByUserId());
        }
        return ResponseEntity.ok(userMapper.usersToUserDTOs(userEntities));
    }
}
