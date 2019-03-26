package com.itstar.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/1/23
 * @description:
 * @version: 1.0
 * 监听单节点内容
 */
public class WatchDemo {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String connectString = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181";
        int sessionTimeout = 3000;
        ZooKeeper zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });

        byte[] data = zkCli.getData("/delireba", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("监听路径为" + event.getPath());
                System.out.println("监听类型为" + event.getType());
                System.out.println("监听被修改了");
            }
        }, null);

        System.out.println(new String(data));

        Thread.sleep(Long.MAX_VALUE);
    }
}
