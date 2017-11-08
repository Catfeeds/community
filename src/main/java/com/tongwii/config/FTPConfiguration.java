package com.tongwii.config;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * FTP配置
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Configuration
public class FTPConfiguration {
    private final TongWiiProperties tongWiiProperties;

    public FTPConfiguration(TongWiiProperties tongWiiProperties) {
        this.tongWiiProperties = tongWiiProperties;
    }

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("192.168.0.190");
        sf.setUsername("www");
        sf.setPassword("www");
        sf.setControlEncoding(StandardCharsets.UTF_8.displayName());
        return new CachingSessionFactory<>(sf);
    }

    @Bean
    @ServiceActivator(inputChannel = "toFtpChannel")
    public MessageHandler ftpHandler() {
        FtpMessageHandler handler = new FtpMessageHandler(ftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("/data/upload/test"));
        handler.setAutoCreateDirectory(true);
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof File) {
                return ((File) message.getPayload()).getName();
            } else {
                throw new IllegalArgumentException("File expected as payload.");
            }
        });
        return handler;
    }

    @Bean
    public FtpRemoteFileTemplate template() {
        return new FtpRemoteFileTemplate(ftpSessionFactory());
    }
}
