package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.UserContactEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IUserContactService;
import com.tongwii.service.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/userContact")
public class UserContactController {
    @Autowired
    private IUserContactService userContactService;
    @Autowired
    private IUserService userService;
    private TongWIIResult result = new TongWIIResult();

    /**
     * 添加联系人
     * @param userContactEntity
     * @return result
     * */
    @RequestMapping(value = "/addUserContacts", method = RequestMethod.POST)
    public TongWIIResult addUserContacts(@RequestBody UserContactEntity userContactEntity){
        if(userContactEntity == null){
            result.errorResult("联系人实体为空!");
        }
        UserEntity friend = userService.findById(userContactEntity.getFriendId());
        UserEntity user = userService.findById(userContactEntity.getUserId());
        if(friend == null){
            result.errorResult("添加的用户不存在");
            return result;
        }
        if(user == null){
            result.errorResult("用户信息不存在");
            return result;
        }
        List<UserContactEntity> userContactEntities = userContactService.findByUserId(userContactEntity.getUserId());
        int contact = 0;
        for(UserContactEntity userContactEntity1 : userContactEntities){
            if(userContactEntity1.getFriendId().equals(userContactEntity.getFriendId())){
                contact++;
            }
        }
        if(contact > 0){
            result.errorResult("该联系人信息已存在!");
            return result;
        }
        userContactService.addUserContact(userContactEntity);
        result.successResult("添加联系人成功!", userContactEntity);
        return result;
    }

    /**
     * 获取联系人列表
     * @param userId
     * @return result
     * */
    @RequestMapping(value = "/userContacts/{userId}", method = RequestMethod.GET)
    public TongWIIResult getContactsByUserId(@PathVariable String userId){
        if(userId == null || userId.isEmpty()){
            result.errorResult("用户信息不存在!");
            return result;
        }
        List<UserContactEntity> userContactList = userContactService.findByUserId(userId);
        if(CollectionUtils.isEmpty(userContactList)){
            result.errorResult("此用户没有联系人!");
            return result;
        }
        JSONArray jsonArray = new JSONArray();

        for(UserContactEntity userContactEntity : userContactList){
            UserEntity userEntity = userService.findById(userContactEntity.getFriendId());
            JSONObject object = new JSONObject();
            object.put("contactName", userEntity.getName());
            object.put("contactAccount", userEntity.getAccount());
            object.put("contactNick", userEntity.getNickName());
            object.put("contactDesc", userContactEntity.getDes());
            object.put("contactPhone", userEntity.getPhone());
            object.put("contactId", userContactEntity.getId());
            jsonArray.add(object);
        }
        result.successResult("联系人列表获取成功!", jsonArray);
        return result;
    }

    /**
     * 删除联系人
     * @param contactId
     * @return result
     * */
    @RequestMapping(value="/delectContact/{contactId}", method = RequestMethod.GET)
    public TongWIIResult delectContact(@PathVariable String contactId){
        if(contactId == null || contactId.isEmpty()){
            result.errorResult("联系人信息获取失败!");
            return result;
        }
        try{
            userContactService.delete(contactId);
        }catch (Exception e){
            result.errorResult("该联系人信息不存在!");
            return result;
        }
        result.successResult("联系人信息删除成功!");
        return result;
    }
}
