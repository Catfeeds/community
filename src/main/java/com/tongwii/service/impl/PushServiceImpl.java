package com.tongwii.service.impl;

import com.gexin.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.constant.MessageConstants;
import com.tongwii.constant.PushConstants;
import com.tongwii.constant.UserConstants;
import com.tongwii.domain.MessageEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.service.*;
import com.tongwii.util.PushTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Service
@Transactional
public class PushServiceImpl implements IPushService{
    @Autowired
    private IUserRoomService userRoomService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private IUserService userService;
    // 个推应用配置参数设置
    private static String APPID = "JmL38ikDvT6BxVij26iff2";
    private static String APPKEY = "5ZtUygJRzc6Ssg2jt3dvE7";
    private static String MASTERSECRET = "eE5Y3cabZE9Wy54eAwz6Z1";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    private PushTemplate pushTemplate = new PushTemplate();
    public static TongWIIResult result = new TongWIIResult();


    /**
     * 指定列表推送功能模块
     *
     * @param pushInfo
     * @param roomCode
     * @return result
     * */
    public TongWIIResult listMesssgePush(MessageEntity pushInfo, String roomCode){
        ListMessage message = new ListMessage();
        IPushResult ret = null;
        IGtPush push = new IGtPush(host,APPKEY, MASTERSECRET);
        List<String> clientIdList = new ArrayList<String>();
        List<Target> targetList = new ArrayList<Target>();
        // 通过roomCode获取roomId
        String roomId = roomService.findRoomByCode(roomCode).getId();
        // 通过roomid获取用户实体
        List<UserEntity> userEntities = userRoomService.findUsersByRoomId(roomId);
        if(userEntities == null){
            result.setStatus(UserConstants.USER_DISABLE);
            result.setInfo("获取用户列表信息失败!");
            result.setData(userEntities);
            return result;
        }else{
            for (UserEntity users : userEntities){
                clientIdList.add(users.getClientId());
            }
        }
        for(int index=0; index < clientIdList.size(); index++){
            Target target = new Target();
            target.setAppId(APPID);
            target.setClientId(clientIdList.get(index));
            System.out.println("clientid"+"["+index+"]:"+clientIdList.get(index));
            targetList.add(target);

        }
        // 封装了透传消息的具体透传内容
        JSONObject transmissionContent = new JSONObject();
        transmissionContent.put("title",pushInfo.getTitle());
        transmissionContent.put("text",pushInfo.getContent());
//        将推送者名称返回给前台
        // 此处需要返回发送者的昵称，因此需要对这个数据做处理
        UserEntity userEntity = userService.findById(pushInfo.getCreateUserId());
        transmissionContent.put("sender",userEntity.getNickName());

        // 获取发送消息的时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        System.out.println(dateString);

        transmissionContent.put("pushTime",dateString);
        // 创建透传消息模板
        TransmissionTemplate template = pushTemplate.transmissionTemplate( transmissionContent.toString());
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);

        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        try{
            ret = push.pushMessageToList(taskId, targetList);
        }catch (RequestException e){
            result.setData(false);
            result.setInfo("网络不给力，推送超时!");
            result.setStatus(PushConstants.PUSH_ERROR);
            return result;
        }
        if(ret != null){
            System.out.println("wahaha:"+ret.getResponse().toString());
            if(ret.getResponse().get("result").equals("ok")){
                // 添加通知表
                pushInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                if(pushInfo.getMessageTypeId().equals("PUSH_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.PUSH_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("NEW_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.NEW_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("NOTIFY_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.NOTIFY_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("VOTE_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.VOTE_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("COMPLAINT_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.COMPLAINT_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("NEW_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.NEW_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("HURRY_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.HURRY_MESSAGE.toString());
                }

                if(pushInfo.getMessageTypeId().equals("NODE_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.NODE_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("DYNAMIC_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.DYNAMIC_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("ONLINE_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.ONLINE_MESSAGE.toString());
                }
                if(pushInfo.getMessageTypeId().equals("REPLAY_MESSAGE")){
                    pushInfo.setMessageTypeId(MessageConstants.REPLAY_MESSAGE.toString());
                }
                messageService.save(pushInfo);
                result.setData(transmissionContent);
                result.setInfo("推送成功!");
                result.setStatus(PushConstants.PUSH_SUCCESS);
                return result;
            }else{
                result.setInfo("获取住户信息失败!");
                result.setStatus(PushConstants.PUSH_ERROR);
                return result;
            }

        }else{
            System.out.println("服务器响应异常");
            result.setInfo("服务器响应异常!");
            result.setStatus(PushConstants.PUSH_ERROR);
            return result;
        }
    }
}
