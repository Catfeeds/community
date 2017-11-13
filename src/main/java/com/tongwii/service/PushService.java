package com.tongwii.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tongwii.bean.PushMessage;
import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.Message;
import com.tongwii.domain.MessageType;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.gateWay.PushGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by admin on 2017/7/13.
 */
@Service
@Transactional
public class PushService {
    private final PushGateway gateway;
    private final MessageService messageService;

    public PushService(PushGateway gateway, MessageService messageService) {
        this.gateway = gateway;
        this.messageService = messageService;
    }

    public void push(PushMessage pushMessage, String pushTopic) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            gateway.push(objectMapper.writeValueAsString(pushMessage), pushTopic);
            String userId = SecurityUtils.getCurrentUserId();
            Message messageEntity = new Message();
            messageEntity.setTitle(pushMessage.getTitle());
            messageEntity.setContent(pushMessage.getMessage());
            messageEntity.setCreateUserId(userId);
            messageEntity.setProcessState(MessageConstants.PROCESSED);
            messageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            MessageType messageType = new MessageType();
            messageType.setCode(MessageConstants.PUSH_MESSAGE);
            messageEntity.setMessageType(messageType);
            messageService.save(messageEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
