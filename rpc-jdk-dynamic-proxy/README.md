动态代理

1. 只学一个方法
   方法的作用：在运行时，动态创建一组指定的接口的实现类对象！（在运行时，创建实现了指定的一组接口的对象）

interface A {
}

interface B {
}

Object o = 方法(new Class[]{A.class,B.class})
o对象所属的类它实现了A和B两个接口！


-------------


Object proxyObject = Proxy.newProxyInstance(ClassLoader classLoader, Class[] interfaces, InvocationHandler h);
1. 方法作用：动态创建实现了interfaces数组中所有指定接口的实现类对象！
   参数；
1. ClassLoader：类加载器！
* 它是用来加载类的，把.class文件加载到内存，形成Class对象！
2. Class[]　interfaces：指定要实现的接口们
3. InvocationHandler：代理对象的所有方法(个别不执行，例如getClass()不会执行)都会调用InvocationHandler的invoke()方法。


---------------------------------------------------------

2. 动态代理作用
   最终是学习AOP（面向切面编程），它与装饰者模式有点相似，它比装饰者模式还要灵活！

----------------------------------------------------------

InvocationHandler

public Object invoke(Object proxy, Method method, Object[] args);

这个invoke()方法在什么时候被调用！
1. 在代理对象被创建时?错误的！
2. 在调用代理对象所实现接口中的方法时?正确的！

* Object proxy：当前对象，即代理对象！在调用谁的方法！
* Method method：当前被调用的方法（目标方法）
* Object[] args：实参！

----------------------------

目标对象：被增强的对象
代理对象：需要目标对象，然后在目标对象上添加了增强后的对象！
目标方法：增强的内容

代理对象 = 目标对象 + 增强






































