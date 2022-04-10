package com.yangtzelsl.controller;

import com.yangtzelsl.service.FirstClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstClientController {
    /**
     * 如果没有本地的服务逻辑，直接注入远程的服务代理对象也可。
     * 使用@DubboReference注解。
     * 不推荐。
     * 因为服务的调用者，一般都会有本地逻辑。直接使用远程服务，可能破坏分层体系。
     */
    @Autowired
    private FirstClientService clientService;

    @RequestMapping(value="/first", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String first(String name){
        return clientService.sayHello(name);
    }
}
