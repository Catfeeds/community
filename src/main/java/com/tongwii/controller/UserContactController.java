package com.tongwii.controller;

import com.tongwii.domain.User;
import com.tongwii.domain.UserContact;
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
     * @param userContact
     * @return result
     * */
    @PostMapping(value = "/addUserContacts")
    public ResponseEntity addUserContacts(@RequestBody UserContact userContact) {
        String userId = SecurityUtils.getCurrentUserId();

        List<UserContact> userContactEntities = userContactService.findByUserId(userId);
        int contact = 0;
        for(UserContact userContact1 : userContactEntities){
            if(userContact1.getFriendId().equals(userContact.getFriendId())){
                contact++;
            }
        }
        if(contact > 0){
            return ResponseEntity.badRequest().body("该联系人信息已存在!");
        }
        userContact.setUserId(userId);
        userContactService.addUserContact(userContact);
        return ResponseEntity.ok(userContact);
    }

    /**
     * 获取联系人列表
     *
     * @return result tong wii result
     */
    @GetMapping
    public ResponseEntity getContactsByUserId() {
        String userId = SecurityUtils.getCurrentUserId();
        List<UserContact> userContactList = userContactService.findByUserId(userId);
        if (CollectionUtils.isEmpty(userContactList)) {
            return ResponseEntity.badRequest().body("此用户没有联系人!");
        }
        Map<String, List<Map>> contactMap = new TreeMap<>();
        for (UserContact userContact : userContactList) {
            User friend = userContact.getUserByFriendId();
            String pinYin = userContact.getDes();
            if (StringUtils.isEmpty(pinYin)) {
                pinYin = friend.getNickName();
            }
            String sortString = PinYinUtil.converterToFirstChar(pinYin);
            Map<String, Object> object = new HashMap<>();
            object.put("contactAccount", friend.getAccount());
            object.put("contactName", friend.getName());
            object.put("contactDesc", userContact.getDes());
            object.put("contactClientId", friend.getClientId());
            object.put("contactPhone", friend.getPhone());
            if (StringUtils.isNotEmpty(friend.getAvatarFileId())) {
                object.put("contactPhoto", friend.getFileByAvatarFileId().getFilePath());
            }
            object.put("contactId", userContact.getId());
            if (sortString.matches("[A-Z]")) {
                if (!contactMap.containsKey(sortString)) {
                    contactMap.put(sortString, new ArrayList<>());
                }
                contactMap.get(sortString).add(object);
            } else {
                if (!contactMap.containsKey(UserContact.UNKNOWN_NAME)) {
                    // 存放不是A-Z字母的联系人名称
                    contactMap.put(UserContact.UNKNOWN_NAME, new ArrayList<>());
                }
                contactMap.get(UserContact.UNKNOWN_NAME).add(object);
            }
        }
        return ResponseEntity.ok(contactMap);
    }

    /**
     * 删除联系人
     * @param contactId
     * @return result
     * */
    @PutMapping(value="/delectContact/{contactId}")
    public ResponseEntity delectContact(@PathVariable String contactId) {
        userContactService.delete(contactId);
        return ResponseEntity.ok("联系人信息删除成功!");
    }
}
