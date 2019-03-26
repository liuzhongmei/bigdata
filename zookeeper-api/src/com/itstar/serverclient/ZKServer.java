package com.itstar.serverclient;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/23
 * @description: 服务端
 * @version: 1.0
 */
public class ZKServer {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //1.连接zkServer
        ZKServer zkServer = new ZKServer();
        zkServer.getConnect();

        //2.注册节点信息 服务器ip添加到zk中
        zkServer.regist(args[0]);

        //3.业务逻辑处理
        zkServer.build(args[0]);
    }
    private String connectString = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181";
    private int sessionTimeout = 3000;
    private ZooKeeper zkCli = null;
    //定义父节点
    private String parentNode = "/servers";
    /**
     * 连接zkServer
     */
    public void getConnect() throws IOException {
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
    }

    /**
     * 注册信息
     */
    public void regist(String hostname) throws KeeperException, InterruptedException {
        String node = zkCli.create(parentNode + "/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(node);
    }

    /**
     * 构建服务端
     */
    public void build(String hostname) throws InterruptedException {
        System.out.println(hostname + "服务器上线了！");
        Thread.sleep(Long.MAX_VALUE);
    }
}
