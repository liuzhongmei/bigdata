package com.itstaredu.rpc.protocol;

/**
 * 协议
 */
public interface ClientNamenodeProtocol {
    //1. 定义协议的id
    public static final long versionID = 1L;

    //2. 定义方法(拿到元数据的方式)
    public String getMetaData(String path);

}
