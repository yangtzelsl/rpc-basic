package com.yangtzelsl.netty.serialize.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * protostuff是一个基于protobuf实现的序列化方法，
 * 它较于protobuf最明显的好处是，在几乎不损耗性能的情况下做到了不用我们写.proto文件来实现序列化
 */
public class ProtostuffTest {
    static RuntimeSchema<Po> poSchema = RuntimeSchema.createFrom(Po.class);

    private static byte[] decode(Po po){
        return ProtostuffIOUtil.toByteArray(po, poSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    private static Po ecode(byte[] bytes){
        Po po = poSchema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, po, poSchema);
        return po;
    }

    public static void main(String[] args) {
        InnerPo innerPo = new InnerPo(1, "InnerPo1");
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        Po po = new Po(1, "Fong", "备注", 24, new int[]{1,2,3,4},innerPo,list);
        byte[] bytes = decode(po);
        System.out.println(bytes.length);
        Po newPo = ecode(bytes);
        System.out.println(newPo);
    }
}
