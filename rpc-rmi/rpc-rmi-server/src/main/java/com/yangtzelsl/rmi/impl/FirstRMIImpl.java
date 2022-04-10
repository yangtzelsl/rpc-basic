package com.yangtzelsl.rmi.impl;

import com.yangtzelsl.rmi.api.FirstInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// 实现远程服务接口。 所有的远程服务实现，必须是Remote接口直接或间接实现类。
// 如果不会创建基于RMI的服务标准实现，可以继承UnicastRemoteObject类型。
// RMI强制要求，所有的方法必须抛出RemoteException，包括构造方法。
public class FirstRMIImpl extends UnicastRemoteObject implements FirstInterface, Remote {
    public FirstRMIImpl() throws RemoteException{
        super();
    }
    @Override
    public String first(String name) throws RemoteException {
        System.out.println("客户端请求参数是：" + name);
        return "你好，" + name;
    }
}
