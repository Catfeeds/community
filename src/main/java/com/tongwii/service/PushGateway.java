package com.tongwii.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@MessagingGateway(defaultRequestChannel = "pushToAll")
public interface PushGateway {

    @Gateway(requestChannel = "pushToAll")
    void pushAll(String message);

    @Gateway(requestChannel = "pushToSelectUsers")
    void push(String message);
}
