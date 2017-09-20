package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.constant.CommunityConstants;
import com.tongwii.po.UserContactEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IUserContactService;
import com.tongwii.service.IUserService;
import com.tongwii.util.PinYinUtil;
import com.tongwii.util.TokenUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    /**
     * 添加联系人
     * @param userContactEntity
     * @return result
     * */
    @RequestMapping(value = "/addUserContacts", method = RequestMethod.POST)
    public Result addUserContacts(@RequestBody UserContactEntity userContactEntity){
        if(userContactEntity == null){
            return Result.errorResult("联系人实体为空!");
        }
        UserEntity friend = userService.findById(userContactEntity.getFriendId());
        UserEntity user = userService.findById(userContactEntity.getUserId());
        if(friend == null){
            return Result.errorResult("添加的用户不存在");
        }
        if(user == null){
            return Result.errorResult("用户信息不存在");
        }
        List<UserContactEntity> userContactEntities = userContactService.findByUserId(userContactEntity.getUserId());
        int contact = 0;
        for(UserContactEntity userContactEntity1 : userContactEntities){
            if(userContactEntity1.getFriendId().equals(userContactEntity.getFriendId())){
                contact++;
            }
        }
        if(contact > 0){
            return Result.errorResult("该联系人信息已存在!");
        }
        userContactService.addUserContact(userContactEntity);
        return Result.successResult(userContactEntity);
    }

    /**
     * 获取联系人列表
     *
     * @param token the token
     * @return result tong wii result
     */
    @GetMapping()
    public TongWIIResult getContactsByUserId(){
        try {
            // TODO: 2017/9/19
            String userId = "";
            List<UserContactEntity> userContactList = userContactService.findByUserId(userId);
            if(CollectionUtils.isEmpty(userContactList)){
                return Result.errorResult("此用户没有联系人!");
            }
            Map<String, JSONArray> contactMap = new TreeMap<>();
            for(UserContactEntity userContactEntity : userContactList){
                UserEntity friend = userContactEntity.getUserByFriendId();
                String pinYin = friend.getName();
                if(pinYin.isEmpty()) {
                    pinYin = friend.getNickName();
                }
                String sortString = PinYinUtil.converterToFirstChar(pinYin);
                JSONObject object = new JSONObject();
                object.put("contactName", friend.getName());
                object.put("contactAccount", friend.getAccount());
                object.put("contactNick", friend.getNickName());
                object.put("contactDesc", userContactEntity.getDes());
                object.put("contactPhone", friend.getPhone());
                if(StringUtils.isNotEmpty(friend.getAvatarFileId())) {
                    object.put("contactPhoto", friend.getFileByAvatarFileId().getFilePath());
                }
                object.put("contactId", userContactEntity.getId());
                if (sortString.matches("[A-Z]")) {
                    if (!contactMap.containsKey(sortString)) {
                        contactMap.put(sortString, new JSONArray());
                    }
                    contactMap.get(sortString).add(object);
                } else {
                    if(!contactMap.containsKey(UserContactEntity.UNKNOWN_NAME)) {
                        // 存放不是A-Z字母的联系人名称
                        contactMap.put(UserContactEntity.UNKNOWN_NAME, new JSONArray());
                    }
                    contactMap.get(UserContactEntity.UNKNOWN_NAME).add(object);
                }
            }
            return Result.successResult(contactMap);
        } catch (Exception e) {
            return Result.successResult("联系人列表获取失败!");
        }
    }

    /**
     * 删除联系人
     * @param contactId
     * @return result
     * */
    @RequestMapping(value="/delectContact/{contactId}", method = RequestMethod.GET)
    public Result delectContact(@PathVariable String contactId){
        if(contactId == null || contactId.isEmpty()){
            return Result.errorResult("联系人信息获取失败!");
        }
        try{
            userContactService.delete(contactId);
        }catch (Exception e){
            return Result.errorResult("该联系人信息不存在!");
        }
        return Result.successResult("联系人信息删除成功!");
    }
}
