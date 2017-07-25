package com.tongwii.controller;

import com.tongwii.bean.PageInfo;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.MessageEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IMessageService;
import com.tongwii.service.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IMessageService messageService;
    @Autowired
    private IUserService userService;
    private static TongWIIResult result = new TongWIIResult();

    /**
     *添加消息接口
     *
     * @param messageEntity
     * @return result
     **/
    @RequestMapping(value = "/insertMessage", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult insertMessage(@RequestBody MessageEntity messageEntity){
        if(messageEntity.getTitle().isEmpty() && messageEntity.getContent().isEmpty()){
            result.errorResult("消息体不可为空!");
            return result;
        }
        try {
            messageEntity.setCreateTime( new Timestamp(System.currentTimeMillis()));
            messageService.save(messageEntity);
            result.successResult("消息添加成功!",messageEntity);
        }catch (Exception e){
            result.errorResult("消息添加失败!");
        }
        return result;
    }
    /**
     * 修改消息的进度
     *
     *@param messageEntity
     *@return result
     * */
    @RequestMapping(value = "/updateProcessOfMessage", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult updateProcessOfMessage(@RequestBody MessageEntity messageEntity){
        // 此消息实体包含id与Process的信息，通过id找到该条消息的数据记录，并将Process的状态更改成传来的值
        // 判空
        if(messageEntity.getId().isEmpty() || messageEntity.getProcessState().toString().isEmpty()){
            result.errorResult("消息记录不存在!");
            return result;
        }
        // 此处更改消息进度状态
        result.successResult("修改状态成功!", messageService.updateMessageProcess(messageEntity.getId(),messageEntity.getProcessState()&0xff));
        return null;
    }

    /**
     * 通过消息类型查询消息
     *
     * @param message
     * @return result
     * */
    @RequestMapping(value = "/selectMessageByType", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult selectMessageByType(@RequestHeader("page") Integer page,@RequestBody MessageEntity message){
        if (message.getMessageTypeId() == null || message.getMessageTypeId().isEmpty()){
            result.errorResult("消息类型不可为空!");
            return result;
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(page);
        List<MessageEntity> messageEntities = messageService.selectMessageByType(pageInfo, message.getMessageTypeId(), message.getResidenceId());
        if(messageEntities.isEmpty() || messageEntities==null){
            result.errorResult("信息查询失败!");
            return result;
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
        result.successResult("查询成功!", messageJsonArray);
        return result;
    }

}
