package com.yangtzelsl.rpc.registry;

import com.yangtzelsl.rpc.connection.ZkConnection;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.util.List;

// 注册器工具。
// 通过zk连接对象，和传入的Remote接口实现对象，完成RMI地址的拼接，和保存（保存在zk中）。
// 缺少LocateRegistry对象。 缺少当前类型中属性的赋值过程。整体流程，缺少ZkConnection的创建过程。
public class BjsxtRpcRegistry {
    // 连接对象
    private ZkConnection connection;
    private String ip;
    private int port;

    /**
     * BUG文档， 记录出现的问题，问题的原因（多种），问题的解决方案（应该是多种），[问题的重现]
     * 问题1： 反复注册，会抛出异常。org.apache.zookeeper.KeeperException$NodeExistsException: KeeperErrorCode = NodeExists for /xxx
     *  原因： 节点已存在。不能反复创建
     *  解决办法： 先查询节点，如果存在，则删除。
     * 注册服务的方法
     * 1、拼接RMI的访问地址URI
     * 2、把访问地址URI存储在Zookeeper中。
     * @param serviceInterface - 服务接口类对象。 如： com.bjsxt.service.UserService.class
     *                         接口必须是Remote接口的子接口
     * @param serviceObject - 服务实现类型的对象。 如： new com.bjsxt.service.impl.UserServiceImpl
     *                      实现类型，必须实现serviceInterface，且是Remote接口直接或间接实现类
     * @throws Exception 抛出异常，代表注册失败
     */
    public void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, KeeperException, InterruptedException {
        // rmi= rmi://ip:port/com.bjsxt.service.UserService
        String rmi = "rmi://"+ip+":"+port+"/"+serviceInterface.getName();

        // 拼接一个有规则的zk存储节点命名。
        String path = "/bjsxt/rpc/"+serviceInterface.getName();


        List<String> children = connection.getConnection().getChildren("/bjsxt/rpc", false);
        if(children.contains(serviceInterface.getName())){
            // 节点存在，需要删除
            Stat stat = new Stat();
            connection.getConnection().getData(path, false, stat);
            connection.getConnection().delete(path, stat.getCversion());
        }

        connection.getConnection().create(path, rmi.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 把服务对象，在RMI的Registry中注册。
        Naming.rebind(rmi, serviceObject);
    }

    /**
     * 根据服务接口类型，访问zk，获取RMI的远程代理对象。
     * 1、 拼接一个zk中的节点名称
     * 2、 访问zk，查询节点中存储的数据
     * 3、 根据查询的结果，创建一个代理对象。
     * @return
     */
    public <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws IOException, KeeperException, InterruptedException, NotBoundException {
        // 拼接zk中的节点名称
        String path = "/bjsxt/rpc/"+serviceInterface.getName();

        // 查询节点中存储的数据
        byte[] datas = connection.getConnection().getData(path, false, null);
        // 把查询的字节数组，翻译成RMI的访问地址
        String rmi = new String(datas);
        // 创建代理对象
        Object obj = Naming.lookup(rmi);
        return (T) obj;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ZkConnection getConnection() {
        return connection;
    }

    public void setConnection(ZkConnection connection) {
        this.connection = connection;
    }
}
