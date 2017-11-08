package com.tongwii.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 推送消息端点
 *
 * @author Zeral
 * @date 2017-09-25
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface PushGateway {

    @Gateway(requestChannel = "mqttOutboundChannel")
    void push(String message, @Header(MqttHeaders.TOPIC) String topic);

}
