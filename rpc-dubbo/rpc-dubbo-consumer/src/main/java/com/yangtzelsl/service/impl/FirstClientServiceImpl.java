package com.yangtzelsl.service.impl;

import com.yangtzelsl.service.FirstClientService;
import com.yangtzelsl.service.FirstService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * 客户端本地服务实现，和服务端服务代码实现无关系。
 */
@Service
public class FirstClientServiceImpl implements FirstClientService {
    // 定义一个服务接口引用
    // 基于Dubbo，为这个接口，创建代理对象。
    // 在2.7.7以前版本，使用Reference注解， 2.7.7使用DubboReference注解
    // 注解的功能，就是访问zk，获取URI统一资源路径，并创建代理对象。
    /*
     * 默认环境中，Dubbo的Provider是不分组的。
     * 可以指定服务分组。目的，多客户端并行生效；多服务端分组管理。
     * 商业开发中，服务都需要分组。
     * 当服务端将Provider分组后，消费端引用的consumer必须指定要定位的分组。
     * 否则抛出异常： IllegalStateException， 检查Provider状态发生异常。
     *
     * 默认环境中，Dubbo的Provider是没有版本设定的。
     * 可以指定版本。目的是：多版本并行提供服务。
     * 商业开发中，服务都需要版本。
     * 当服务端将Provider定义版本后，消费端引用的consumer必须指定要定位的版本。
     * 否则抛出异常： IllegalStateException， 检查Provider状态发生异常。
     *
     * 负载均衡： 在Dubbo中，负载均衡的配置有3个位置：
     *  1、 @Service|@DubboService注解上的属性。 loadbalance。代表，为Provider集群定义负载均衡策略
     *      默认是random。
     *  2、 @Reference|@DubboReference注解上的属性。 loadbalance。 代表，为Consumer定义调用Provider集群
     *      时使用的负载均衡策略。 默认，无。采用Provider提供的负载均衡策略。
     *  3、 在配置文件中配置。 配置文件是当前应用全局配置。 注解是当前类型的对象对应的Provider或Consumer配置
     *  商业常规开发中，配置文件和注解联合使用。配置文件定义全局。特定的服务或消费
     *  定义局部。
     *
     * 配置文件中的常用配置： payload。
     *  payload是指请求容量和响应容量的限制。默认限制为8M。8388608字节
     *  也就是，请求头+请求体容量必须小于8M。响应头+响应体容量必须小于8M。
     *  只能在配置文件中进行配置，只要配置了，请求和响应的容量同步变更。
     */
    @DubboReference(group = "firstGroup", version = "1.0.1")
    private FirstService firstService;
    /**
     * 当前服务代码，本地无具体逻辑，实现由远程服务提供。需要远程过程调用。RPC调用。
     * 使用Dubbo技术，创建一个本地的代理对象，调用远程的服务逻辑。
     * 需要一个服务的标准，基于标准，创建代理，并调用远程服务。
     * @param name
     * @return
     */
    @Override
    public String sayHello(String name) {
        System.out.println("远程服务代理对象类型是：" + firstService.getClass().getName());
        System.out.println("客户端参数是：" + name);
        String result = firstService.sayHello(name);
        System.out.println("服务端返回结果是：" + result);

        // 模拟文件上传
        // 模拟8M以内的文件上传
        byte[] datas = new byte[8*1024*1024 - 1];
        for(int i = 0; i < datas.length; i++){
            datas[i] = 10;
        }
        System.out.println("8M以内的文件上传：" + firstService.testPayload(datas));

        // 模拟8M以上的文件上传
        byte[] datas2 = new byte[8*1024*1024 + 1];
        for(int i = 0; i < datas2.length; i++){
            datas2[i] = 10;
        }
        System.out.println("8M以上的文件上传：" + firstService.testPayload(datas2));
        return result;
    }
}
