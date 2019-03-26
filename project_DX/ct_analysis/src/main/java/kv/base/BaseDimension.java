package kv.base;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author: lzm
 * @date: 2019/3/19
 * @description:
 * @version: 1.0
 */
public abstract class BaseDimension implements WritableComparable<BaseDimension> {

    /**
     *
     * @param baseDimension
     * @return
     */
    public abstract int compareTo(BaseDimension baseDimension);

    /**
     *
     * @param output
     * @throws IOException
     */
    public abstract void write(DataOutput output) throws IOException;

    /**
     *
     * @param in
     * @throws IOException
     */
    public abstract void readFields(DataInput in) throws IOException;
}
