package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jorden
 * @date 2018/4/19 9:13
 * <p>
 * 发布消息的回调类
 * <p>
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 * <p>
 * public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 * <p>
 * public void connectionLost(Throwable cause)在断开连接时调用。
 * <p>
 * public void deliveryComplete(MqttDeliveryToken token))
 * 接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 * 由 MqttClient.connect 激活此回调。
 */
@Component
public class PushCallback implements MqttCallbackExtended {
    private String topic = "aiot/+/thing/forward";
    @Resource
    private SubscribeConn subscribeConn;
    private static final Logger LOGGER = LoggerFactory.getLogger(PushCallback.class);

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.info("PushCallback connectionLost 连接断开");
         subscribeConn.reconnect();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String msg = new String(mqttMessage.getPayload());
        LOGGER.debug("接收消息主题:{},接收消息Qos:{},接收消息内容:{}", s, mqttMessage.getQos(), msg);
        try {

            LOGGER.info("执行业务... data [topic:{}, message:{}]", s, msg);

        } catch (Exception e) {
            LOGGER.error("消息处理失败，", e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.info("deliveryComplete:{}", iMqttDeliveryToken.isComplete());
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        //client.subscribe(topics,Qos);//具体订阅代码
        LOGGER.info("connectComplete ----------{},serverURI:{}", reconnect, serverURI);
        try {
            subscribeConn.getMqttClient().subscribe(topic, 2);
        } catch (MqttException e) {
            LOGGER.error("订阅{}发生错误，{}", topic, e.getMessage());
        }
    }

}
