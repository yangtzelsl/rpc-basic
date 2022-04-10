package com.yangtzelsl.rpc.connection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

// 专门提供Zookeeper连接的自定义类型。
public class ZkConnection {
    // 保存zk的地址。格式是ip:port。 如：192.168.89.140:2181
    private String zkServer;
    // 保存会话超时时间。
    private int sessionTimeout;
    public ZkConnection(){
        super();
        // 设置默认值。
        this.zkServer = "localhost:2181";
        this.sessionTimeout = 10000;
    }
    public ZkConnection(String zkServer, int sessionTimeout){
        this.zkServer = zkServer;
        this.sessionTimeout = sessionTimeout;
    }
    public ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(zkServer, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
    }
}
