dubbo://169.254.188.84:20880/com.bjsxt.service.FirstService?anyhost=true&application=first-dubbo-provider&default=true&deprecated=false&dubbo=2.0.2&dynamic=true&generic=false&interface=com.bjsxt.service.FirstService&methods=sayHello&pid=8560&release=2.7.7&side=provider&timestamp=1596419887417

Dubbo本身就是一个工具，作用有限，核心是：
1、 把服务端Provider注册到注册中心中。把URL存到Zookeeper中。
2、 访问注册中心，获取URL地址，并创建代理对象。

负载均衡：
  必须是并发。至少是集中请求。