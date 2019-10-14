/**
 * 3.
 * 一个简单的聊天程序
 * 案例：socket服务端和客户端，多个客户端能够互相访问的一个socket通信程序
 * 服务端功能 （监控在线客户端，群发）
 * - 服务端监测客户端 上线/下线，在服务端控制台打印
 * - 服务端监测客户端 加入/离开，通知其他在线客户端，当前客户端 加入/离开 服务端
 *
 * - 接收客户端的消息，同时将消息转发到其他在线客户端
 *
 * 客户端功能
 * - 接收服务端的消息
 * - 可以从控制台录入消息，并发送到服务端
 *
 */
package com.jorgezhong.netty.example.chat;