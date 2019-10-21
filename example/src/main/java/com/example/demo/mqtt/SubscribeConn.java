package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: jorden
 * @Date: 2018/4/19 15:25
 * @Description: 消息订阅者配置类
 */
@Component
@Configuration
@EnableConfigurationProperties({MqttConfiguration.class})
public class SubscribeConn implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribeConn.class);

    @Resource
    private MqttConfiguration mqttConfiguration;
    @Resource
    private PushCallback callback;
    private MqttConnectOptions mqttConnectOptions;
    private MqttAsyncClient mqttClient;

    public void reconnect() {
        while (!mqttClient.isConnected()) {
            try {
                mqttClient.reconnect();
            } catch (MqttException e) {
                LOGGER.error("重连失败", e);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    //ignore
                }
            }
        }
        LOGGER.info("重连成功");
    }

    /**
     * 连接emq服务器
     */
    public MqttAsyncClient getConn() {
        try {
            // host为主机名，clientId即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientId的保存形式，默认为以内存保存
            mqttClient = new MqttAsyncClient(mqttConfiguration.getHost(),
                                             mqttConfiguration.getSubscribeClientId(),
                                             new MemoryPersistence());
            // MQTT的连接设置
            mqttConnectOptions = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            mqttConnectOptions.setCleanSession(true);
            // 设置连接的用户名
            mqttConnectOptions.setUserName(mqttConfiguration.getUsername());
            // 设置连接的密码
            mqttConnectOptions.setPassword(mqttConfiguration.getPassword().toCharArray());
            // 设置超时时间 单位为秒
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setConnectionTimeout(mqttConfiguration.getConnectionTimeout());
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            mqttConnectOptions.setKeepAliveInterval(mqttConfiguration.getKeepAliveInterval());
            //配置多个服务器列表，一个挂掉会自动切换到其他正常的服务器，挂掉的服务器正常后，客户端会再次切换回来
            if (mqttConfiguration.getHosts() != null && mqttConfiguration.getHosts().length > 0) {
                mqttConnectOptions.setServerURIs(mqttConfiguration.getHosts());
            }
            // 设置回调
            mqttClient.setCallback(callback);
            mqttClient.connect(mqttConnectOptions).waitForCompletion();
            LOGGER.info("连接服务器成功");
        } catch (Exception e) {
            LOGGER.info("连接服务器失败", e);
        }
        return mqttClient;
    }

    public MqttConfiguration getMqttConfiguration() {
        return mqttConfiguration;
    }

    public MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }


    public MqttAsyncClient getMqttClient() {
        return mqttClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getConn();
    }

}
