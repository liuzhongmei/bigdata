package com.itstaredu.rpc.protocol.impl;

import com.itstaredu.rpc.protocol.ClientNamenodeProtocol;

public class MyNameNode implements ClientNamenodeProtocol {
    @Override
    public String getMetaData(String path) {
        return path + ": 3 - {BLK_1,BLK_2,BLK_3...}";
    }
}
