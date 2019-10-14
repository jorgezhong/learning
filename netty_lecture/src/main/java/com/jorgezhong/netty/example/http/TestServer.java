package com.jorgezhong.netty.example.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Project <learning>
 * Created by jorgezhong on 2019/10/12 14:27.
 */
public class TestServer {

    public static void main(String[] args) throws InterruptedException {


        //定义两个事件循环组
        //该线程组接收连接，接收到连接之后发送给worker
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //真正完成连接后的一些工作的处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            //启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)//定义事件组
                    .channel(NioServerSocketChannel.class)//定义要初始化的channel，反射的方式创建
                    .childHandler(new TestServerInitializer());//定义子处理器

            //绑定到8899的端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();

            channelFuture.channel().closeFuture().sync();

        } finally {
            //优雅的关闭事件事件组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
