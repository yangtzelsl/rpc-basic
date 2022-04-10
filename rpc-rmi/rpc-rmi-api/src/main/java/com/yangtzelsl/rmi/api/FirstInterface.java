package com.yangtzelsl.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

// 定义一个远程服务接口。RMI强制要求，必须是Remote接口的实现。
public interface FirstInterface extends Remote {
    // RMI强制要求，所有的远程服务方法，必须抛出RemoteException。
    String first(String name) throws RemoteException;
}
