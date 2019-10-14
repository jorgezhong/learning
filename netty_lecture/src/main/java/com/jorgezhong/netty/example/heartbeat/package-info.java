/**
 * 心跳：使用IdleStateEvent监测IdleState事件
 *  - READER_IDLE（读空闲）：客户端一定时间内未发送数据到服务端，服务端触发该事件
 *  - WRITER_IDLE（写空闲）：服务端一定事件内未发送数据到客户端，服务端触发该事件
 *      - 测试：启动服务端和客户端，客户端控制台不断发送数据到服务端，超市后服务端打印“写空闲”
 *  - ALL_IDLE（读写空闲）：客户端和服务端超时都未发送数据到对方
 *      - 测试：allIdleTime修改未小于readIdleTime 和 writeIdleTime
 *      - 修改：new IdleStateHandler(5,7,3 , TimeUnit.SECONDS)
 * 心跳客户端使用chat下的MyChatClient即可
 *
 *
 *
 * Project <learning>
 * Created by jorgezhong on 2019/10/14 17:59.
 */
package com.jorgezhong.netty.example.heartbeat;