package com.yangtzelsl.service.impl;

import com.yangtzelsl.service.FirstService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 第一个Dubbo服务提供者。 Provider
 * RPC的服务端逻辑。
 * Dubbo框架提供了自定义注解，用于服务的注册和发现。
 * 当开发的代码是服务提供者的时候，需要把服务注册到注册中心，等待客户端调用。
 * 当开发的代码是服务消费者的时候，需要从注册中心发现服务，并创建代理对象，访问远程服务。
 *
 * Dubbo自定义了注解Service，用于服务的注册过程。
 * 注意导入的包，不要和Spring的Service注解混淆。
 *
 * 从2.7.7版本开始，Service注解过时，提供新注解DubboService。
 * 功能和Dubbo的Service注解完全一致。
 *
 * DubboService - 功能是： 在Spring容器启动的时候，创建bean对象。并拼接一个
 *  URL，保存到指定的注册中心。
 */
@DubboService(loadbalance = "roundrobin", group = "firstGroup", version = "1.0.1")
public class FirstServiceImpl implements FirstService {
    @Override
    public String testPayload(byte[] datas) {
        System.out.println("客户端传递的数据长度是：" + datas.length + "字节");
        return "处理结束";
    }

    @Override
    public String sayHello(String name) {
        System.out.println("客户端传递的数据是：" + name);
        return "你好，" + name;
    }
}
