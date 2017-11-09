package com.tongwii.service.gateWay;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.File;

/**
 * FTP端口
 *
 * @author Zeral
 * @date 2017-11-08
 */
@MessagingGateway
public interface FtpGateway {
    @Gateway(requestChannel = "toFtpChannel")
    void sendToFtp(File file, @Header(FileHeaders.FILENAME) String fileName, @Header(FileHeaders.REMOTE_DIRECTORY) String filrUrl);
}
