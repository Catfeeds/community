package com.tongwii.service;

import org.springframework.integration.annotation.MessagingGateway;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface Gateway {
    void sendToMqtt(String data);
}
