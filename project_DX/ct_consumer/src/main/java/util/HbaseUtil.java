package util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author: lzm
 * @date: 2019/3/18
 * @description:
 * @version: 1.0
 * 1、NameSpace ====>  命名空间
 * 2、createTable ===> 表
 * 3、isExistTable   ====>  判断表是否存在
 * 4、Region、RowKey、分区键
 */
public class HbaseUtil {
    /**
     * 初始化命名空间
     * @param conf 配置对象
     * @param nameSpace 命名空间名
     * @throws IOException
     */
    public static void initNameSpace(Configuration conf, String nameSpace) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        //命名空间描述器
        NamespaceDescriptor nd = NamespaceDescriptor.create(nameSpace).addConfiguration("AUTHOR", "lzm").build();
        admin.createNamespace(nd);
        close(admin, connection);
    }

    /**
     * 关闭
     * @param admin
     * @param connection
     */
    private static void close(Admin admin, Connection connection) {
        try {
            if(null != admin){
                admin.close();
            }
            if(null != connection){
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表
     * @param conf
     * @param tableName
     * @param regions
     * @param columnFamily
     */
    public static  void createTable(Configuration conf,String tableName,int regions, String... columnFamily) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        if(!isExistTable(conf,tableName)){
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
            for(String cf:columnFamily){
                //列描述器
                htd.addFamily(new HColumnDescriptor(cf));
            }
            //创建表
            admin.createTable(htd,genSplitKeys(regions));
            //关闭
            close(admin,connection);
        }else{
            System.out.println("表已经存在");
        }
    }

    /**
     * 创建分区键
     * 例如：{"00|", "01|", "02|", "03|", "04|", "05|"}
     * @param regions
     * @return
     */
    private static byte[][] genSplitKeys(int regions) {
        String[] keys = new String[regions];
        DecimalFormat df = new DecimalFormat("00");
        for(int i = 0;i < regions;i++){
            keys[i] = df.format(i) + "|";
        }

        byte[][] splitkeys = new byte[regions][];
        //排序 保证分区键是有序的
        TreeSet<byte[]> treeSet = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for(int i = 0;i < regions;i++){
            treeSet.add(Bytes.toBytes(keys[i]));
        }
        Iterator<byte[]> iterator = treeSet.iterator();
        int index = 0;
        while(iterator.hasNext()){
            byte[] next = iterator.next();
            splitkeys[index++] = next;
        }
        return splitkeys;
    }

    /**
     * 判断表是否存在
     * @param conf
     * @param tableName
     * @return
     * @throws IOException
     */
    public static boolean isExistTable(Configuration conf,String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        boolean result = admin.tableExists(TableName.valueOf(tableName));
        close(admin,connection);
        return result;
    }

    /**
     * 生成rowkey:regionCode_caller_buildTime_callee_flag_duration
     *
     * @param regionCode 散列的键
     * @param caller     主叫
     * @param buildTime  建立时间
     * @param callee     被叫
     * @param flag       标明是主叫还是被叫
     * @param duration   通话持续时间
     * @return 返回rowkey
     */
    public static String genRowkey(String regionCode, String caller, String buildTime, String callee, String flag, String duration) {
        StringBuilder sb = new StringBuilder();
        sb.append(regionCode + "_")
                .append(caller + "_")
                .append(buildTime + "_")
                .append(callee + "_")
                .append(flag + "_")
                .append(duration);
        return sb.toString();
    }

    /**
     * 生成分区号
     * @param caller 主叫
     * @param buildTime 通话建立时间
     * @param regions region个数
     * @return 返回分区号
     */
    public static String genRegionCode(String caller, String buildTime, int regions) {
        int len = caller.length();
        //取出主叫后四位
        String lastPhone = caller.substring(len - 4);
        //取出年和月 buildTime:2018-06-22 13:27:21
        String ym = buildTime
                .replaceAll("-", "")
                .replaceAll(" ", "")
                .replaceAll(":", "")
                .substring(0, 6);
        //离散操作1
        Integer x = Integer.valueOf(lastPhone) ^ Integer.valueOf(ym);
        //离散操作2
        int y = x.hashCode();
        //生成分区号
        int regionCode = y % regions;
        //格式化分区号
        DecimalFormat df = new DecimalFormat("00");
        return df.format(regionCode);
    }

}
