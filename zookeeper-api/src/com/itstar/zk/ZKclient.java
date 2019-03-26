package com.itstar.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author: lzm
 * @date: 2019/1/23
 * @description:
 * @version: 1.0
 */
public class ZKclient {
    private String connectString = "192.168.230.201:2181,192.168.230.202:2181,192.168.230.203:2181";
    private int sessionTimeout = 3000;
    private ZooKeeper zkCli = null;

    /**
     * 初始化客户端
     */
    @Before
    public void init() throws IOException {
        zkCli = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            /**
             * 回调监听
             * @param event
             */
            @Override
            public void process(WatchedEvent event) {
//                System.out.println(event.getPath()  + "\t" + event.getState() + "\t" + event.getType());

                try {
                    List<String> children = zkCli.getChildren("/", true);
                    for (String c:children){
                        System.out.println(c);
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建子节点
     */
    @Test
    public void createZnode() throws KeeperException, InterruptedException {
        String path = zkCli.create("/bbq", "烧烤".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    /**
     * 获取子节点
     */
    @Test
    public void getChild() throws KeeperException, InterruptedException {
        List<String> children = zkCli.getChildren("/", true);
        for (String c:children){
            System.out.println(c);
        }

        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 删除节点
     */
    @Test
    public void rmChildData() throws KeeperException, InterruptedException {
        byte[] data = zkCli.getData("/bbq", true, null);
        System.out.println(new String(data));
        zkCli.delete("/bbq",-1);
    }

    /**
     * 修改数据
     */
    @Test
    public void setData() throws KeeperException, InterruptedException {
        zkCli.setData("/delireba","lala".getBytes(),-1);
    }

    /**
     * 判断节点是否存在
     */
    @Test
    public void testExist() throws KeeperException, InterruptedException {
        Stat exists = zkCli.exists("/hunter", false);
        System.out.println(exists == null ? "not exists" : "exists");
    }
}
