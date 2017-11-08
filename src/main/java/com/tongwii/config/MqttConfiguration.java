package com.tongwii.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.UUID;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Configuration
@IntegrationComponentScan
public class MqttConfiguration {
    private final TongWiiProperties tongWiiProperties;

    public MqttConfiguration(TongWiiProperties tongWiiProperties) {
        this.tongWiiProperties = tongWiiProperties;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(tongWiiProperties.getMqtt().getServerURIs());
        return factory;
    }


    /**
     * MessageChannel method creates a new mqttInputChannel
     * to connect to the Broker with a single threaded connection.
     * Downstream components are connected via Direct Channel.
     *
     * @return   DirectChannel single threaded connection
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * MessageProducer generates a clientID for the subscriber by using a randomUUID
     * for creating a connection to a broker. Creates a new
     * connection adapter to a broker by setting the clientID, MqttClientFactory,
     * topic given to subscribe to, Completion Timeout, Converter,
     * Qos, and the Output Channel for numMessages to go to.
     *
     * @return   adapter for the mqttInbound connection
     */
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter(UUID.randomUUID().toString(), mqttClientFactory(), tongWiiProperties.getMqtt().getTopics());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(tongWiiProperties.getMqtt().getQos());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * Message handler calculates the amount of messages that have come in
     * from the subscribed topic and posts the result.
     *
     * @return   message is the numMessages amount
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> System.out.println("---------------->" + message.getPayload());
    }


    /**
     * Outbound Channel Adapter
     * 出站通道适配器，可以配置clientId, broker 服务器地址，MqttMessageConverter (optional) mqtt消息转换器，
     * 连接工厂信息，默认的服务质量，默认的topic，默认的retained值，默认的qos值，异步发送消息（不用等待确认），
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
            new MqttPahoMessageHandler(UUID.randomUUID().toString(), mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setAsyncEvents(true);
        messageHandler.setConverter(new DefaultPahoMessageConverter());
        messageHandler.setDefaultQos(tongWiiProperties.getMqtt().getDefaultQos());
        messageHandler.setDefaultTopic(tongWiiProperties.getMqtt().getDefaultTopic());
        messageHandler.setDefaultRetained(tongWiiProperties.getMqtt().getDefaultRetained());
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new PublishSubscribeChannel();
    }
}
