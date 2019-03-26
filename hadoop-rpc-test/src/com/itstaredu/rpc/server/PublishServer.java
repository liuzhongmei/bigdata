package com.itstaredu.rpc.server;

import com.itstaredu.rpc.protocol.ClientNamenodeProtocol;
import com.itstaredu.rpc.protocol.impl.MyNameNode;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;

import java.io.IOException;

public class PublishServer {
    public static void main(String[] args) throws IOException {
        //1. 构建rpc框架
        Builder builder = new RPC.Builder(new Configuration());

        //2. 绑定地址
        builder.setBindAddress("localhost");

        //3. 绑定端口号
        builder.setPort(7777);

        //4. 绑定协议
        builder.setProtocol(ClientNamenodeProtocol.class);

        //5. 调用协议的实现类
        builder.setInstance(new MyNameNode());

        //6. 创建服务
        Server server = builder.build();
        server.start();
    }
}
