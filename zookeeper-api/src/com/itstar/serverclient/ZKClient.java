package com.itstar.serverclient;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lzm
 * @date: 2019/1/23
 * @description: 客户端
 * @version: 1.0
 */
public class ZKClient {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //1.获取连接
        ZKClient zkClient = new ZKClient();
        zkClient.getConnect();

        //2.监听服务器节点信息
        zkClient.getServers();

        //3.业务逻辑（一直监听）
        zkClient.getWatch();
    }
    private String connectString = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181";
    private int sessionTimeout = 3000;
    ZooKeeper zkCli = null;
    /**
     * 连接集群
     */
    public void getConnect() throws IOException {
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    System.out.println("event = [" + event + "]");
                    getServers();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 监听服务的节点信息
     */
    public void getServers() throws KeeperException, InterruptedException {
        //监听父节点
        List<String> children = zkCli.getChildren("/servers", true);
        //创建集合存储服务器列表
        ArrayList<String> serverList = new ArrayList<>();
        //获取每个节点的数据
        for(String c:children){
            byte[] data = zkCli.getData("/servers/" + c, true, null);
            serverList.add(new String(data));
        }

        //打印服务器列表
        System.out.println(serverList);
    }

    /**
     *
     */
    public void getWatch() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }
}
