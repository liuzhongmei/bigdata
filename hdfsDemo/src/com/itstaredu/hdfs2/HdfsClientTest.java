package com.itstaredu.hdfs2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * API
 */
public class HdfsClientTest {
    public FileSystem fs;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        // 客户端加载配置
        Configuration conf = new Configuration();
        // 设置副本个数
        conf.set("dfs.replication", "2");
        // 设置块大小
        conf.set("dfs.blocksize", "64m");
        // 构造客户端
        fs = FileSystem.get(new URI("hdfs://192.168.230.201:9000"), conf, "root");
    }

    /**
     * 上传本地文件到hdfs
     */
    @Test
    public void copyFromLocalFile() throws IOException {
        // 上传
        fs.copyFromLocalFile(new Path("e:/hello.txt"), new Path("/hello.txt"));
        // 关闭资源
        fs.close();
    }

    /**
     * 下载hdfs文件
     * java.io.FileNotFoundException: java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset.
     * 配置HADOOP环境变量
     * java.io.FileNotFoundException: Could not locate Hadoop executable: E:\software_package\hadoop-2.8.4\bin\winutils.exe
     * 需要将winutils.exe文件拷贝到 E:\software_package\hadoop-2.8.4\bin目录下
     * 下载的是linux环境的tar包，解压之后直接用的，需要导入编译好的windows环境
     */
    @Test
    public void copyToLocalFile() throws IOException {
        fs.copyToLocalFile(new Path("/aa.txt"), new Path("e:/"));
        // 关闭资源
        fs.close();
    }

    /**
     * 在hdfs中创建文件夹
     * hdfs dfs -mkdir /文件名
     */
    @Test
    public void hdfsMkdir() throws IOException {
        // 创建文件夹
        fs.mkdirs(new Path("/hadoop"));
        // 关闭资源
        fs.close();
    }

    /**
     * 在hdfs中移动/修改文件
     * hdfs dfs -mv /hdfs文件路径 /hdfs文件路径
     */
    @Test
    public void hdfsRename() throws IOException {
        // 修改文件名
        fs.rename(new Path("/hello/aa.txt"), new Path("/hadoop/hello.txt"));
        // 关闭资源
        fs.close();
    }

    /**
     * 在hdfs中删除文件夹
     * 删除文件夹:hdfs dfs -rm -r /
     * 删除文件:hdfs dfs -rm
     */
    @Test
    public void hdfsDelete() throws IOException {
//        fs.delete(new Path("/hello/a.txt"));
        // 删除文件：参数1：删除的文件路径；参数2：是否递归删除
        fs.delete(new Path("/hadoop"), true);
        // 关闭资源
        fs.close();
    }

    /**
     * 查询hdfs下指定的目录信息
     * hdfs dfs -ls /
     */
    @Test
    public void hdfsLs() throws IOException {
        // 获取目录信息
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();
            System.out.println("文件的路径:" + status.getPath());
            System.out.println("文件的块大小：" + status.getBlockSize());
            System.out.println("文件的长度：" + status.getLen());
            System.out.println("文件的副本数：" + status.getReplication());
            System.out.println("文件的块信息：" + Arrays.toString(status.getBlockLocations()));
            System.out.println("*************************************");
        }
        // 关闭资源
        fs.close();
    }

    /**
     * 判断是文件还是文件夹
     */
    @Test
    public void findAtHdfs() throws IOException {
        // 1.展示状态信息
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));

        // 2.遍历所有文件
        for (FileStatus ls:fileStatuses) {
            if(ls.isFile()){
                System.out.println("文件----f----"+ls.getPath().getName());
            }else{
                System.out.println("文件夹----d----"+ls.getPath().getName());
            }
        }
    }
}
