package com.tongwii.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Configuration
@IntegrationComponentScan
public class MqttConfiguration {

    private final ApplicationProperties applicationProperties;

    public MqttConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
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
            new MqttPahoMessageDrivenChannelAdapter("tcp://106.14.227.231:1883", "JavaSample",
                "MQTT Examples");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
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

}
