package com.example.demo.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jorden
 * @Description: emq配置类
 * @date 2018/4/18 14:19
 */
@Component // 不加这个注解的话, 使用@Autowired 就不能注入进去了
@ConfigurationProperties(prefix = "mqtt") // 配置文件中的前缀
public class MqttConfiguration {
    private String host;
    private String username;
    private String password;
    private Integer qos;
    private String[] hosts;
    private Integer connectionTimeout;
    private Integer keepAliveInterval;
    private String publishClientId;
    private String subscribeClientId;

    public String getHost() {
        return host;
    }

    public MqttConfiguration setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MqttConfiguration setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MqttConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getQos() {
        return qos;
    }

    public MqttConfiguration setQos(Integer qos) {
        this.qos = qos;
        return this;
    }

    public String[] getHosts() {
        return hosts;
    }

    public MqttConfiguration setHosts(String[] hosts) {
        this.hosts = hosts;
        return this;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public MqttConfiguration setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public Integer getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public MqttConfiguration setKeepAliveInterval(Integer keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
        return this;
    }

    public String getPublishClientId() {
        return publishClientId;
    }

    public MqttConfiguration setPublishClientId(String publishClientId) {
        this.publishClientId = publishClientId;
        return this;
    }

    public String getSubscribeClientId() {
        return subscribeClientId;
    }

    public MqttConfiguration setSubscribeClientId(String subscribeClientId) {
        this.subscribeClientId = subscribeClientId;
        return this;
    }
}
