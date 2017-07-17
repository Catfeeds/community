package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.MessageEntity;
import com.tongwii.po.RoomEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by admin on 2017/7/13.
 */
@RestController
@RequestMapping("/message")
public class PushController {

    @Autowired
    private IPushService pushService;
    private TongWIIResult result = new TongWIIResult();
    // 按照一个房间推送消息
    @RequestMapping(value = "/pushMessageToSingleRoom", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult pushMessageToSingleRoom(@RequestHeader("roomCode")String roomCode,@RequestBody MessageEntity messageEntity){
        // 判空
        if(messageEntity.getTitle().isEmpty() && messageEntity.getContent().isEmpty()){
            result.errorResult("消息不可为空!");
            return result;
        }
        if(roomCode.isEmpty()){
            result.errorResult("推送目标不可为空!");
            return result;
        }
       /* if(userId.isEmpty()){
            result.errorResult("非法推送!");
            return result;
        }*/
        result = pushService.listMesssgePush(messageEntity,roomCode);
        return result;
    }

    @RequestMapping(value = "/test")
    public void test(@RequestHeader("userId")String userId, @RequestBody MessageEntity messageEntity){
        String user = userId;
        String title = messageEntity.getTitle();
    }
}
