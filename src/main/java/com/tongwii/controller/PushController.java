package com.tongwii.controller;

import com.tongwii.constant.PushConstants;
import com.tongwii.dto.PushMessageDTO;
import com.tongwii.service.PushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 推送消息Controller
 *
 * 社区管理员和超级管理员拥有推送权限
 */
@RestController
@RequestMapping("/api/push")
//@PreAuthorize("hasAnyAuthority(T(com.tongwii.constant.AuthoritiesConstants).ADMIN, T(com.tongwii.constant.AuthoritiesConstants).COMMUNITY_ADMIN)")
public class PushController {

    private final PushService pushService;

    public PushController(PushService pushService) {this.pushService = pushService;}

    /**
     * 全推
     * @param pushMessage
     * @return
     */
    @PostMapping("/pushAll")
    public ResponseEntity pushAll(@RequestBody PushMessageDTO pushMessage) {
        if (StringUtils.isEmpty(pushMessage.getMessage())) {
            return ResponseEntity.badRequest().body("消息内容不能为空!");
        } else {
            pushService.push(pushMessage, PushConstants.PUSH_ALL_TOPIC);
            return ResponseEntity.ok("消息推送成功!");
        }
    }

    /**
     * 个推
     * @param pushMessage
     * @return
     */
    @PostMapping("/push")
    public ResponseEntity push(@RequestBody PushMessageDTO pushMessage) {
        if (StringUtils.isEmpty(pushMessage.getMessage())) {
            return ResponseEntity.badRequest().body("消息内容不能为空!");
        } else {
            pushService.push(pushMessage, PushConstants.PUSH_SELECTED_TOPIC);
            return ResponseEntity.ok("消息推送成功！");
        }
    }

}
