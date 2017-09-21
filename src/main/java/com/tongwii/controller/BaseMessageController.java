package com.tongwii.controller;

import com.tongwii.core.Result;
import com.tongwii.domain.MessageEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.service.MessageService;
import com.tongwii.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by admin on 2017/7/14.
 */
@RestController
@RequestMapping("/message")
public class BaseMessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    /**
     *添加消息接口
     *
     * @param messageEntity
     * @return result
     **/
    @RequestMapping(value = "/insertMessage", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public Result insertMessage(@RequestBody MessageEntity messageEntity){
        if(messageEntity.getTitle().isEmpty() && messageEntity.getContent().isEmpty()){
            return Result.errorResult("消息体不可为空!");
        }
        try {
            messageEntity.setCreateTime( new Timestamp(System.currentTimeMillis()));
            messageService.save(messageEntity);
            return Result.successResult(messageEntity);
        }catch (Exception e){
            return Result.failResult("消息添加失败!");
        }

    }
    /**
     * 修改消息的进度
     *
     *@param messageEntity
     *@return result
     * */
    @RequestMapping(value = "/updateProcessOfMessage", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public Result updateProcessOfMessage(@RequestBody MessageEntity messageEntity){
        // 此消息实体包含id与Process的信息，通过id找到该条消息的数据记录，并将Process的状态更改成传来的值
        // 判空
        if(messageEntity.getId().isEmpty() || messageEntity.getProcessState().toString().isEmpty()){
            return Result.errorResult("消息记录不存在!");
        }
        // 此处更改消息进度状态
        messageService.updateMessageProcess(messageEntity.getId(),messageEntity.getProcessState()&0xff);
        return Result.successResult("修改状态成功!");
    }

    /**
     * 通过消息类型查询消息
     *
     * @param message
     * @return result
     * */
    @RequestMapping(value = "/selectMessageByType", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public Result selectMessageByType(@RequestHeader("page") Integer page, @RequestBody MessageEntity message){
        if (message.getMessageTypeId() == null || message.getMessageTypeId().isEmpty()){
            return Result.errorResult("消息类型不可为空!");
        }
        Pageable pageInfo = new PageRequest(page, 5);
        List<MessageEntity> messageEntities = messageService.selectMessageByType(pageInfo, message.getMessageTypeId(), message.getResidenceId());
        if(messageEntities.isEmpty() || messageEntities==null){
            return Result.errorResult("信息查询失败!");
        }
        JSONArray messageJsonArray = new JSONArray();
        JSONObject messageObject = new JSONObject();
        for (MessageEntity messageEntity : messageEntities){
            messageObject.put("id", messageEntity.getId());
            messageObject.put("title",messageEntity.getTitle());
            messageObject.put("content", messageEntity.getContent());
            String time = messageEntity.getCreateTime().toString();
            String createTime = time.substring(0,time.length()-2);
            messageObject.put("createTime",createTime);
            // 通过userId查询userName
            UserEntity userEntity = userService.findById(messageEntity.getCreateUserId());
            messageObject.put("createUser", userEntity.getAccount());
            messageJsonArray.add(messageObject);
        }
        return Result.successResult().add("pageInfo", pageInfo).add("messageInfo",messageJsonArray);
    }



}
