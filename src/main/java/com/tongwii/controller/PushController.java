package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.MessageEntity;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.MessageService;
import com.tongwii.service.PushGateway;
import com.tongwii.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


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
    @Autowired
    private MessageService messageService;

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

    /**
     * 推送消息接口
     * @param messageEntity
     * @return
     */
    @PostMapping("/pushMessage")
    public ResponseEntity TestPush(@RequestBody MessageEntity messageEntity) {
        String userId = SecurityUtils.getCurrentUserId();
         if(StringUtils.isEmpty(messageEntity.getContent())){
             return ResponseEntity.badRequest().body("消息内容不能为空!");
         }else{
             messageEntity.setCreateUserId(userId);
             messageEntity.setProcessState(MessageConstants.PROCESSED);
             messageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
             messageEntity.setMessageTypeId(MessageConstants.PUSH_MESSAGE.toString());
             messageService.save(messageEntity);
             gateway.sendToMqtt(messageEntity.getContent());
             return ResponseEntity.ok("消息推送成功!");
         }
    }
}
