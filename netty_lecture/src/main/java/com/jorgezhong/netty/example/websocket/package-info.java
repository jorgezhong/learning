/**
 * 5. 案例：netty对websocket支持的示例
 *
 * 本例编写了websocket服务端，websocket协议是基于http协议的因此需要配置
 *  - HttpServerCodec
 *  - HttpObjectAggregator 聚合http消息
 *  - WebSocketServerProtocolHandler ： 处理websocket协议的handler,定义路由
 *
 * netty对websocket消息的支持是基于 frame的，WebSocketFrame
 *
 * websocket的handlerRemoved是监听客户端断开的，但是当断网或飞行模式是无法监听到的
 * 此时需要心跳机制，IdleStateHandler
 *
 * Project <learning>
 * Created by jorgezhong on 2019/10/15 4:48.
 */
package com.jorgezhong.netty.example.websocket;