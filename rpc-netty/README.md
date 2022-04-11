# 原生NIO存在的问题

- NIO的类库和API繁杂，使用麻烦，需要熟练掌握Selector,ServerSocketChannel,SocketChannel,ByteBuffer等
- 需要具备其它的额外的技能，要熟悉Java多线程编程，因为NIO编程涉及到Reactor模式，必须对多线程和网络非常熟悉，才能编写出高质量的NIO程序
- 开发工作量和难度都非常大，例如客户端面临断开重连，网络闪断，半包读写，失败缓存，网络拥塞和异常流的处理等
- JDK NIO的Bug,例如臭名昭著的Epoll Bug，它会导致Selector空轮询，最终导致CPU 100%,直到JDK 1.7版本该问题仍旧存在，没有被根本解决

# 线程模型介绍
## 传统阻塞I/O服务模型
## 单Reactor单线程
## 单Reactor多线程
## 主从Reactor多线程
## Netty线程模式