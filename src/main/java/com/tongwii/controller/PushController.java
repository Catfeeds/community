package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.domain.MessageEntity;
import com.tongwii.service.PushGateway;
import com.tongwii.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by admin on 2017/7/13.
 */
@RestController
@RequestMapping("/message")
public class PushController {

    @Autowired
    private PushService pushService;
    @Autowired
    private PushGateway gateway;
    private TongWIIResult result = new TongWIIResult();
    // 根据单个房间推送消息
    @PostMapping("/pushMessageToSingleRoom")
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
    }


    @PostMapping("testPush")
    public ResponseEntity TestPush(String message) {
        gateway.sendToMqtt(message);
        return ResponseEntity.ok(null);
    }
}
