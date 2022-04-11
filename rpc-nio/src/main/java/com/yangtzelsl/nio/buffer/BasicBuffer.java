package com.yangtzelsl.nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {

        //举例说明Buffer 的使用 (简单说明)
        // ByteBuffer ShortBuffer IntBuffer LongBuffer FloatBuffer DoubleBuffer CharBuffer
        //创建一个Buffer, 大小为 5, 即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer 存放数据
//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);
        for(int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put( i * 2);
        }

        //如何从buffer读取数据
        //flip()是将buffer转换，读写切换(!!!)
        // mark <= position <= limit <= capacity
        // flip() 方法底层的源码如下
        /*
        public final Buffer flip() {
            limit = position; //读数据不能超过5
            position = 0;
            mark = -1;
            return this;
        }
         */
        intBuffer.flip();
        intBuffer.position(1);
        System.out.println(intBuffer.get());
        intBuffer.limit(3);//限制了3，则下标从1,2开始
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
