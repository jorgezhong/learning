package com.jorgezhong.netty.example.heartbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/14 17:23.
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //IdleStateHandler：针对空闲监测提供的一个处理器，客户端向服务器发送（read : 读空闲），服务器向客户端发送(write : 写空闲)，两种事件加起来（all ：读写空闲）
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
