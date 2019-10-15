/**
 * 6. RPC序列化和反序列化的一个框架，由Google提供的protobuf库
 *
 * 序列化和反序列化：编码（Encode）与解码（Decode）
 *
 * 几乎所有的RPC框架，都存在代码生成，生成的代码完成实际的序列化和反序列化以及网络传输的过程。
 *
 * 生成的代码在客户端称之为：stub (桩)
 * 生成的代码在服务端称之为：skeleton（骨架）
 *
 * RPC：Remote Procedure Call：远程过程调用
 * RMI：Remote Method Invocation：远程方法调用
 *
 * 区别：RMI只限于JAVA写的服务端和客户端，RPC是跨语言和跨平台的
 *
 * 原理：
 * 1.定义一个接口说明文件：描述了对象（结构体）、对象成员、接口方法等一系列信息
 * 2.通过RPC框架所提供的编译器，将接口说明文件编译成具体的语言文件。
 * 3.在客户端与服务器端分别引入RPC编译器所生成的文件，即可像调用本地方法一样调用远程方法。
 *
 */
package com.jorgezhong.netty.example.protobuf;