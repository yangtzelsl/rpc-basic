package com.yangtzelsl.demo2;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 我们必须要掌握的是当前这个案例！
 */
public class Demo2 {
	@Test
	public void fun1() {
		Waiter manWaiter = new ManWaiter();//目标对象
		/*
		 * 给出三个参数，来创建方法，得到代理对象
		 */
		ClassLoader loader = this.getClass().getClassLoader();
		Class[] interfaces = {Waiter.class};
		InvocationHandler h = new WaiterInvocationHandler(manWaiter);//参数manWaiter表示目标对象
		// 得到代理对象，代理对象就是在目标对象的基础上进行了增强的对象！
		Waiter waiterProxy = (Waiter)Proxy.newProxyInstance(loader, interfaces, h);
		
		waiterProxy.serve();//前面添加“您好”，　后面添加“再见”
	}
}

class WaiterInvocationHandler implements InvocationHandler {
	private Waiter waiter;//目标对象
	
	public WaiterInvocationHandler(Waiter waiter) {
		this.waiter = waiter;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("您好！");
		this.waiter.serve();//调用目标对象的目标方法
		System.out.println("再见！");
		return null;
	}
}
