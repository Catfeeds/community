package com.tongwii.controller;

import com.tongwii.domain.UserContactEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.UserContactService;
import com.tongwii.util.PinYinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/userContact")
public class UserContactController {
    @Autowired
    private UserContactService userContactService;

    /**
     * 添加联系人
     * @param userContactEntity
     * @return result
     * */
    @PostMapping(value = "/addUserContacts")
    public ResponseEntity addUserContacts(@RequestBody UserContactEntity userContactEntity){
        String userId = SecurityUtils.getCurrentUserId();

        List<UserContactEntity> userContactEntities = userContactService.findByUserId(userId);
        int contact = 0;
        for(UserContactEntity userContactEntity1 : userContactEntities){
            if(userContactEntity1.getFriendId().equals(userContactEntity.getFriendId())){
                contact++;
            }
        }
        if(contact > 0){
            return ResponseEntity.badRequest().body("该联系人信息已存在!");
        }
        userContactEntity.setUserId(userId);
//        userContactEntity.setFriendId(friend.getId());
        userContactService.addUserContact(userContactEntity);
        return ResponseEntity.ok(userContactEntity);
    }

    /**
     * 获取联系人列表
     *
     * @return result tong wii result
     */
    @GetMapping()
    public ResponseEntity getContactsByUserId(){
        try {
            String userId = SecurityUtils.getCurrentUserId();
            List<UserContactEntity> userContactList = userContactService.findByUserId(userId);
            if(CollectionUtils.isEmpty(userContactList)){
                return ResponseEntity.badRequest().body("此用户没有联系人!");
            }
            Map<String, List> contactMap = new TreeMap<>();
            for(UserContactEntity userContactEntity : userContactList){
                UserEntity friend = userContactEntity.getUserByFriendId();
                String pinYin = userContactEntity.getDes();
                if(StringUtils.isEmpty(pinYin)) {
                    pinYin = friend.getNickName();
                }
                String sortString = PinYinUtil.converterToFirstChar(pinYin);
                Map<String, Object> object = new HashMap<>();
                object.put("contactAccount", friend.getAccount());
                object.put("contactName", friend.getName());
                object.put("contactDesc", userContactEntity.getDes());
                object.put("contactClientId", friend.getClientId());
                object.put("contactPhone", friend.getPhone());
                if(StringUtils.isNotEmpty(friend.getAvatarFileId())) {
                    object.put("contactPhoto", friend.getFileByAvatarFileId().getFilePath());
                }
                object.put("contactId", userContactEntity.getId());
                if (sortString.matches("[A-Z]")) {
                    if (!contactMap.containsKey(sortString)) {
                        contactMap.put(sortString, new ArrayList());
                    }
                    contactMap.get(sortString).add(object);
                } else {
                    if(!contactMap.containsKey(UserContactEntity.UNKNOWN_NAME)) {
                        // 存放不是A-Z字母的联系人名称
                        contactMap.put(UserContactEntity.UNKNOWN_NAME, new ArrayList());
                    }
                    contactMap.get(UserContactEntity.UNKNOWN_NAME).add(object);
                }
            }
            return ResponseEntity.ok(contactMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("联系人列表获取失败!");
        }
    }

    /**
     * 删除联系人
     * @param contactId
     * @return result
     * */
    @PutMapping(value="/delectContact/{contactId}")
    public ResponseEntity delectContact(@PathVariable String contactId){
        try{
            userContactService.delete(contactId);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("该联系人信息不存在!");
        }
        return ResponseEntity.ok("联系人信息删除成功!");
    }
}
