package com.tongwii.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.Device;
import com.tongwii.domain.Message;
import com.tongwii.domain.MessageType;
import com.tongwii.domain.User;
import com.tongwii.dto.PushMessageDto;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.gateWay.PushGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/7/13.
 */
@Service
@Transactional
public class PushService {
    private final PushGateway gateway;
    private final MessageService messageService;
    private final MessageTypeService messageTypeService;
    private final UserService userService;

    public PushService(PushGateway gateway, MessageService messageService, MessageTypeService messageTypeService, UserService userService) {
        this.gateway = gateway;
        this.messageService = messageService;
        this.messageTypeService = messageTypeService;
        this.userService = userService;
    }

    public void push(PushMessageDto pushMessage, String pushTopic) {
        try {
            if(!CollectionUtils.isEmpty(pushMessage.getUsersId())) {
                Set<String> devices = pushMessage.getUsersId().stream().flatMap(userId -> {
                    User user = userService.findById(userId);
                    return Optional.ofNullable(user.getDevices()).orElse(new HashSet<>()).stream().map(Device::getDeviceId).collect(Collectors.toSet()).stream();
                }).collect(Collectors.toSet());
                pushMessage.setDevicesId(devices);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            gateway.push(objectMapper.writeValueAsString(pushMessage), pushTopic);
            String userId = SecurityUtils.getCurrentUserId();
            Message messageEntity = new Message();
            messageEntity.setTitle(pushMessage.getTitle());
            messageEntity.setContent(pushMessage.getMessage());
            messageEntity.setCreateUserId(userId);
            messageEntity.setProcessState(MessageConstants.PROCESSED);
            messageEntity.setCreateTime(new Date());
            MessageType messageType = messageTypeService.findByCode(MessageConstants.PUSH_MESSAGE);
            messageEntity.setMessageType(messageType);
            messageService.save(messageEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
