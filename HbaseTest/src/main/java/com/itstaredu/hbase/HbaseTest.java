package com.itstaredu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lzm
 * @date: 2019/2/21
 * @description:
 * @version: 1.0
 */
public class HbaseTest {
    public static Configuration conf;
    //获取配置信息
    static {
        conf = HBaseConfiguration.create();
    }

    /**
     * 判断一张表是否存在
     * @param tableName
     * @return
     * @throws IOException
     */
    public static boolean isExist(String tableName) throws IOException {
        //对表操作需要使用HbaseAdmin
        Connection connection = ConnectionFactory.createConnection(conf);
        //管理表
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     * 创建表
     * @param tableName
     */
    public static void createTable(String tableName,String... columnFamilies) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        if(isExist(tableName)){
            System.out.println("表已经存在，请输入其他表名");
        }else{
            //创建表，需要创建一个描述器
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            //创建列族
            for(String cf:columnFamilies){
                hTableDescriptor.addFamily(new HColumnDescriptor(cf));
            }
            admin.createTable(hTableDescriptor);
            System.out.println("表创建成功");
        }
    }

    /**
     * 删除表
     * @return
     */
    public static void deleteTable(String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        if(!isExist(tableName)){
            System.out.println("表不存在，请重新输入");
        }else {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("删除成功");
        }
    }

    /**
     * 添加数据
     * @param tableName 表名
     * @param rowKey
     * @param cf 列族
     * @param column 列名
     * @param value
     * @throws IOException
     */
    public static void add(String tableName,String rowKey,String cf,String column,String value) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value));
        table.put(put);
    }

    /**
     * 根据rowkey删除数据
     * @param tableName
     * @param rowKey
     */
    public static void deleteByRowKey(String tableName,String rowKey) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete d = new Delete(Bytes.toBytes(rowKey));
        table.delete(d);
    }

    /**
     * 删除多行
     * @param tableName
     * @param rowkeys
     * @throws IOException
     */
    public static void deleteAll(String tableName,String...rowkeys) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));
        List<Delete> list = new ArrayList<Delete>();
        for (String rowkey:rowkeys){
            list.add(new Delete(Bytes.toBytes(rowkey)));
        }
        table.delete(list);
    }

    public static void scanAll(String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));
        scan.setStartRow(Bytes.toBytes("1004"));
        scan.setStopRow(Bytes.toBytes("1004"));
        ResultScanner scanner = table.getScanner(scan);
        for(Result rs:scanner){
            Cell[] cells = rs.rawCells();
            for(Cell cell:cells){
                System.out.println("________________________________");
                System.out.println("行键为:"+Bytes.toString(CellUtil.cloneRow(cell)));
                System.out.println("列族为："+Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列为："+Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值为："+Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
    }

    /**
     * 查看指定rowkey值
     * @param tableName
     * @param rowkey
     */
    public static void getRow(String tableName,String rowkey) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));
        //添加过滤条件
//        get.addFamily(Bytes.toBytes("info"));
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for(Cell cell:cells){
            System.out.println("________________________________");
            System.out.println("行键为:"+Bytes.toString(CellUtil.cloneRow(cell)));
            System.out.println("列族为："+Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println("列为："+Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("值为："+Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }
    public static void main(String[] args) throws IOException {
//        boolean isExist = isExist("emp");
//        createTable("hunter","henshuai","feichangshuai");
//        deleteTable("hunter");
//        add("test","1001","info","name","yangmi");
//        deleteByRowKey("test","1001");
//        deleteAll("test","1002","1003");
        scanAll("test");
//        getRow("test","1001");
    }
}
