package com.jorgezhong.netty.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * 在channel被注册之后，自动创建
 * Project <learning>
 * Created by jorgezhong on 2019/10/12 14:39.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("httpServerCodec", new HttpServerCodec())//对web的请求和响应进行编解码
                .addLast("testHttpServerHandler", new TestHttpServerHandler());//自定义Handler, Hello World
    }
}
