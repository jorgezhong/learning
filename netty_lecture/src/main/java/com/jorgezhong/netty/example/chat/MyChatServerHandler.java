package com.jorgezhong.netty.example.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/14 15:27.
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 存储在线连接，一个channel表示一个连接
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 服务端和客户端建立连接了，可以用handler added监测
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //当前建立好连接的通道
        Channel channel = ctx.channel();

        //广播：通知其他在线的连接
        channelGroup.writeAndFlush("【服务器】- " + channel.remoteAddress() + " 加入\n");

        //将连接保存起来
        channelGroup.add(channel);

    }

    /**
     * 客户端断掉了连接，可以用handler removed监测
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】- " + channel.remoteAddress() + " 离开\n");

//        channelGroup.remove(channel);当channel断开，channelGroup会监测并自动移除
    }


    /**
     * 连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线了");

    }

    /**
     * 服务端收到任意一个客户端的消息调用
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //当前连接
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送的消息: " + msg + "\n");
            }else {
                ch.writeAndFlush("【自己】" + msg + "\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
