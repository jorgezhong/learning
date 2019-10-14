package com.jorgezhong.netty.example.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/14 13:39.
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        //以块的方式来去写的一个处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //将HttpMessage 或 HttpContent ( netty read每次接受的只是完整的一个http请求或响应的一部分 )进行一个聚合成完整的FullHttpRequest 或 FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(8192));

        //websocket服务协议处理器（握手、close、ping、pong）,netty数据以各种 frames（WebSocketFrame及其子类） 的形式传递
        //uri: ws://localhost:8899/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
