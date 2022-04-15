package com.yangtzelsl.netty.serialize.protostuff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Po {
    private Integer id;
    private String name;
    private String remark;
    private Integer age;
    private int[] array;
    private InnerPo innerPo;
    private List<String> more;
}
