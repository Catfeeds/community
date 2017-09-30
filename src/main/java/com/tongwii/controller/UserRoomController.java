package com.tongwii.controller;

import com.tongwii.domain.UserEntity;
import com.tongwii.domain.UserRoomEntity;
import com.tongwii.dto.UserDto;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/userRoom")
public class UserRoomController {
    @Autowired
    private UserRoomService userRoomService;
    @Autowired
    private UserMapper userMapper;
    /**
     * 根据roomId查询用户列表
     * @author Yamo
     * @param roomId
     */
    @GetMapping("/getUserByRoomId/{roomId}")
    public ResponseEntity getUserByRoomId(@PathVariable String roomId){
        List<UserRoomEntity> userRoomEntities = userRoomService.findUsersByRoomId(roomId);
        if(CollectionUtils.isEmpty(userRoomEntities)){
            return ResponseEntity.badRequest().body("该住房暂未出售，无住户!");
        }
        List<UserEntity> userEntities = new ArrayList<>();
        for(UserRoomEntity userRoomEntity: userRoomEntities){
            userEntities.add(userRoomEntity.getUserByUserId());
        }
        return ResponseEntity.ok(userMapper.usersToUserDTOs(userEntities));
    }
}
