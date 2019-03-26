package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import util.ConnectionInstance;
import util.HbaseUtil;
import util.PropertiesUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lzm
 * @date: 2019/3/18
 * @description:
 * @version: 1.0
 */
public class HBaseDAO {
    private String namespace;
    private String tableName;
    private int region;
    private static final Configuration conf;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
    private Connection connection;
    private HTable table;

    private List<Put> cacheList = new ArrayList<>();
    static {
        conf = HBaseConfiguration.create();
    }
    public HBaseDAO() {
        try {
            namespace = PropertiesUtil.getProperties("hbase.calllog.namespace");
            tableName = PropertiesUtil.getProperties("hbase.calllog.tablename");
            region = Integer.valueOf(PropertiesUtil.getProperties("hbase.calllog.regions"));

            if (!HbaseUtil.isExistTable(conf, tableName)) {
                HbaseUtil.initNameSpace(conf, namespace);
                HbaseUtil.createTable(conf, tableName, region, "f1","f2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 17269452013,15542823911,2018-08-28 11:58:23,0800
     * @param ori
     */
    public void put(String ori){
        try {
            if (cacheList.size() == 0) {
                connection = ConnectionInstance.getConnection(conf);
                table = (HTable)connection.getTable(TableName.valueOf(tableName));
                table.setAutoFlushTo(false);
                table.setWriteBufferSize(2 * 1024 * 1024);
            }

            String[] split = ori.split(",");
            String caller = split[0];
            String callee = split[1];
            String buildTime = split[2];
            String duration = split[3];
            String buildTimeReplace = sdf2.format(sdf1.parse(buildTime));
            String buildTimeTS = String.valueOf(sdf1.parse(buildTime).getTime());

            //散列的分区号
            String regionCode = HbaseUtil.genRegionCode(caller, buildTime, region);
            String rowKey = HbaseUtil.genRowkey(regionCode, caller, buildTime, callee, "1", duration);

            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("caller"),Bytes.toBytes(caller));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("callee"),Bytes.toBytes(callee));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("buildTimeReplace"),Bytes.toBytes(buildTimeReplace));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("buildTimeTS"),Bytes.toBytes(buildTimeTS));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("flag"),Bytes.toBytes("1"));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("duration"),Bytes.toBytes(duration));

            cacheList.add(put);
            if (cacheList.size() >= 30) {
                table.put(cacheList);
                table.flushCommits();

                table.close();
                cacheList.clear();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
