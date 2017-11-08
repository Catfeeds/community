package com.tongwii.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tongwii.bean.Message;
import com.tongwii.constant.MessageConstants;
import com.tongwii.service.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 推送消息Controller
 *
 * 社区管理员和超级管理员拥有推送权限
 */
@RestController
@RequestMapping("/push")
@PreAuthorize("hasAnyAuthority(T(com.tongwii.constant.AuthoritiesConstants).ADMIN, T(com.tongwii.constant.AuthoritiesConstants).COMMUNITY_ADMIN)")
public class PushController {

    @Autowired
    private PushService pushService;

    /**
     * 全推
     * @param message
     * @return
     */
    @PostMapping("/pushAll")
    public ResponseEntity pushAll(@RequestBody Message message) throws JsonProcessingException {
        if (StringUtils.isEmpty(message.getMessage())) {
            return ResponseEntity.badRequest().body("消息内容不能为空!");
        } else {
            pushService.push(message, MessageConstants.PUSH_ALL_TOPIC);
            return ResponseEntity.ok("消息推送成功!");
        }
    }

    /**
     * 个推
     * @param message
     * @return
     */
    @PostMapping("/push")
    public ResponseEntity push(@RequestBody Message message) throws JsonProcessingException {
        if (StringUtils.isEmpty(message.getMessage())) {
            return ResponseEntity.badRequest().body("消息内容不能为空!");
        } else {
            pushService.push(message, MessageConstants.PUSH_SELECTED_TOPIC);
            return ResponseEntity.ok("消息推送成功！");
        }
    }



    // 根据单个房间推送消息
 /*   @PostMapping("/pushMessageToSingleRoom")
    public TongWIIResult pushMessageToSingleRoom(String roomCode, @RequestBody MessageEntity messageEntity){
        // 判空
        if(messageEntity.getTitle().isEmpty() || messageEntity.getContent().isEmpty() || messageEntity.getTitle().equals("") || messageEntity.getContent().equals("")){
            result.errorResult("消息体为空!");
            return result;
        }
        if(roomCode.isEmpty()){
            result.errorResult("推送目标为空!");
            return result;
        }
        result = pushService.listMesssgePush(messageEntity,roomCode);
        return result;
    }*/

}
