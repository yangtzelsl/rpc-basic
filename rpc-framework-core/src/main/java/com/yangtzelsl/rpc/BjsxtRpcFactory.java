package com.yangtzelsl.rpc;

import com.yangtzelsl.rpc.connection.ZkConnection;
import com.yangtzelsl.rpc.registry.BjsxtRpcRegistry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Properties;

/**
 * 框架入口。
 */
public class BjsxtRpcFactory {
    // 用于保存配置信息
    private static final Properties config = new Properties();
    // 连接对象
    private static final ZkConnection connection ;
    // 注册器对象
    private static final BjsxtRpcRegistry registry;
    // 用于读取初始化的配置对象
    private static final Properties services = new Properties();

    /**
     * 初始化过程。
     * 固定逻辑。在classpath下，提供配置文件，命名为： bjsxt-rpc.properties
     * 配置文件结构固化：
     *   registry.ip=服务端IP地址，默认为localhost
     *   registry.port=服务端端口号，默认为9090
     *   zk.server=Zookeeper访问地址，默认为localhost:2181
     *   zk.sessionTimeout=Zookeeper连接会话超时，默认为10000
     */
    static{
        try {
            // 获取classpath类路径下的配置文件输入流
            InputStream input = BjsxtRpcFactory.class.getClassLoader().getResourceAsStream("bjsxt-rpc.properties");

            // 读取配置文件，初始化配置对象
            config.load(input);
            // 获取服务端IP
            String serverIp = config.getProperty("registry.ip") == null ? "localhost" : config.getProperty("registry.ip");
            // 获取服务端端口号
            int serverPort = config.getProperty("registry.port") == null ? 9090 : Integer.parseInt(config.getProperty("registry.port"));
            // 获取zk服务器地址
            String zkServer = config.getProperty("zk.server") == null ? "localhost:2181" : config.getProperty("zk.server");
            // 获取ZooKeeper客户端会话超时配置
            int zkSessionTimeout = config.getProperty("zk.sessionTimeout") == null ? 10000 : Integer.parseInt(config.getProperty("zk.sessionTimeout"));
            // 创建连接对象
            connection = new ZkConnection(zkServer, zkSessionTimeout);
            // 创建注册器对象
            registry = new BjsxtRpcRegistry();
            // 初始化注册器对象属性
            registry.setIp(serverIp);
            registry.setPort(serverPort);
            registry.setConnection(connection);
            // 创建一个RMI的注册器。
            LocateRegistry.createRegistry(serverPort);

            // 初始化zk中的父节点/bjsxt/rpc
            List<String> children = connection.getConnection().getChildren("/", false);
            // 不存在子节点 /bjsxt
            if(!children.contains("bjsxt")){
                // 创建节点/bjsxt
                connection.getConnection().create("/bjsxt", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            List<String> bjsxtChildren = connection.getConnection().getChildren("/bjsxt", false);
            if(!bjsxtChildren.contains("rpc")){
                connection.getConnection().create("/bjsxt/rpc", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            // 判断在classpath下，是否有一个配置文件，命名为： bjsxt-rpc-services.properties
            // 如果有这个配置，则自动初始化，没有忽略后续逻辑。
            // 配置文件的格式是： 接口全命名=实现类全命名
            InputStream servicesInput = BjsxtRpcFactory.class.getClassLoader().getResourceAsStream("bjsxt-rpc-services.properties");
            if(servicesInput != null){
                // 有配置。初始化
                services.load(servicesInput);
                // 遍历集合services
                for(Object key : services.keySet()){
                    // 通过key查询value
                    Object value = services.get(key);
                    // key是接口的全命名， values是实现类的全命名
                    Class<Remote> serviceInterface = (Class<Remote>) Class.forName(key.toString());
                    Remote serviceObject = (Remote) Class.forName(value.toString()).newInstance();
                    // 有了接口的类对象，和服务的对象。注册
                    registry.registerService(serviceInterface, serviceObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            // 当初始化代码块发生异常的时候，抛出错误，中断虚拟机。
            throw new ExceptionInInitializerError(e);
        }
    }

    // 提供一个快速的注册服务和创建客户端代理对象的静态工具方法
    public static void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, KeeperException, InterruptedException {
        registry.registerService(serviceInterface, serviceObject);
    }

    public static <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws IOException, KeeperException, InterruptedException, NotBoundException {
        return registry.getServiceProxy(serviceInterface);
    }
}
