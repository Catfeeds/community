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
     *�����Ϣ�ӿ�
     *
     * @param messageEntity
     * @return result
     **/
    @RequestMapping(value = "/insertMessage", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult insertMessage(@RequestBody MessageEntity messageEntity){
        if(messageEntity.getTitle().isEmpty() && messageEntity.getContent().isEmpty()){
            result.errorResult("��Ϣ�岻��Ϊ��!");
            return result;
        }
        try {
            messageEntity.setCreateTime( new Timestamp(System.currentTimeMillis()));
            messageService.save(messageEntity);
            result.successResult("��Ϣ��ӳɹ�!",messageEntity);
        }catch (Exception e){
            result.errorResult("��Ϣ���ʧ��!");
        }
        return result;
    }
    /**
     * �޸���Ϣ�Ľ���
     *
     *@param messageEntity
     *@return result
     * */
    @RequestMapping(value = "/updateProcessOfMessage", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult updateProcessOfMessage(@RequestBody MessageEntity messageEntity){
        // ����Ϣʵ�����id��Process����Ϣ��ͨ��id�ҵ�������Ϣ�����ݼ�¼������Process��״̬���ĳɴ�����ֵ
        // �п�
        if(messageEntity.getId().isEmpty() || messageEntity.getProcessState().toString().isEmpty()){
            result.errorResult("��Ϣ��¼������!");
            return result;
        }
        // �˴�������Ϣ����״̬
        result.successResult("�޸�״̬�ɹ�!", messageService.updateMessageProcess(messageEntity.getId(),messageEntity.getProcessState()&0xff));
        return null;
    }

    /**
     * ͨ����Ϣ���Ͳ�ѯ��Ϣ
     *
     * @param messageTypeId
     * @return result
     * */
    @RequestMapping(value = "/selectMessageByType", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
    public TongWIIResult selectMessageByType(@RequestHeader("messageTypeId") String messageTypeId,@RequestBody PageInfo pageInfo){
        if (messageTypeId == null || messageTypeId.isEmpty()){
            result.errorResult("��Ϣ���Ͳ���Ϊ��!");
            return result;
        }
        List<MessageEntity> messageEntities = messageService.selectMessageByType(pageInfo, messageTypeId);
        if(messageEntities.isEmpty() || messageEntities==null){
            result.errorResult("��Ϣ��ѯʧ��!");
            return result;
        }
        JSONArray messageJsonArray = new JSONArray();
        JSONObject messageObject = new JSONObject();
        for (MessageEntity messageEntity : messageEntities){
            messageObject.put("title",messageEntity.getTitle());
            messageObject.put("content", messageEntity.getContent());
            messageObject.put("createTime",messageEntity.getCreateTime());
            // ͨ��userId��ѯuserName
            UserEntity userEntity = userService.findById(messageEntity.getCreateUserId());
            messageObject.put("createUser", userEntity.getAccount());
            messageJsonArray.add(messageObject);
        }
       result.successResult("��ѯ�ɹ�!", messageJsonArray);
        return result;
    }

}
