package com.tongwii.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongwii.constant.MessageConstants;
import com.tongwii.constant.PushConstants;
import com.tongwii.domain.Device;
import com.tongwii.domain.Message;
import com.tongwii.domain.MessageType;
import com.tongwii.domain.User;
import com.tongwii.dto.PushMessageDTO;
import com.tongwii.dto.mapper.PushMessageMapper;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.gateWay.PushGateway;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final PushMessageMapper pushMessageMapper;

    public PushService(PushGateway gateway, MessageService messageService, MessageTypeService messageTypeService, UserService userService, PushMessageMapper pushMessageMapper) {
        this.gateway = gateway;
        this.messageService = messageService;
        this.messageTypeService = messageTypeService;
        this.userService = userService;
        this.pushMessageMapper = pushMessageMapper;
    }

    public void push(PushMessageDTO pushMessage, String pushTopic) {
        try {
            if(!CollectionUtils.isEmpty(pushMessage.getUsersId())) {
                Set<String> devices = pushMessage.getUsersId().stream().flatMap(userId -> {
                    User user = userService.findById(userId);
                    return Optional.ofNullable(user.getDevices()).orElse(new HashSet<>()).stream().map(Device::getDeviceCode).collect(Collectors.toSet()).stream();
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


    /**
     * 每三小时查询一次未发送的消息，并发送
     * 默认全推
     */
    @Scheduled(cron = "0 0 0/3 * * ?")
    public void pushUnhandledMessage() {
        System.out.println("test");
        List<Message> messages = messageService.findByProcessState(MessageConstants.UN_PROCESS);
        Optional.ofNullable(messages).ifPresent(messages1 -> messages1.forEach(message -> {
            PushMessageDTO messageDto = pushMessageMapper.toDto(message);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                gateway.push(objectMapper.writeValueAsString(messageDto), PushConstants.PUSH_ALL_TOPIC);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            message.setProcessState(MessageConstants.PROCESSED);
        }));
    }
}
