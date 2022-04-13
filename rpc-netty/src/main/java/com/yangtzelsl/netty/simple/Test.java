package com.yangtzelsl.netty.simple;

import io.netty.util.NettyRuntime;

public class Test {
    public static void main(String[] args) {
        // 可用核数：12
        System.out.println(NettyRuntime.availableProcessors());
    }
}
