package outputformat;

import org.apache.hadoop.mapreduce.*;
import util.JdbcInstance;

import java.io.IOException;
import java.sql.Connection;

/**
 * @author: lzm
 * @date: 2019/3/19
 * @description:
 * @version: 1.0
 */
public class MySqlOutputFormat extends OutputFormat {
    @Override
    public RecordWriter getRecordWriter(TaskAttemptContext taskAttemptContext){
        //初始化jdbc连接器
        Connection conn = JdbcInstance.getInstance();
        try {
            conn.setAutoCommit(false);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new MysqlRecordWriter(conn);
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return null;
    }

    private class MysqlRecordWriter extends RecordWriter {
        public MysqlRecordWriter(Connection conn) {

        }

        @Override
        public void write(Object o, Object o2) throws IOException, InterruptedException {

        }

        @Override
        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

        }
    }
}
