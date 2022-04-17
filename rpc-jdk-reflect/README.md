15、反射的原理（********理解********）
* 应用在一些通用性比较高的代码 中
* 后面学到的框架，大多数都是使用反射来实现的

	* 在框架开发中，都是基于配置文件开发
		** 在配置文件中配置了类，可以通过反射得到类中的 所有内容，可以让类中的某个方法来执行

	* 类中的所有内容：属性、没有参数的构造方法、有参数的构造方法、普通方法
	
	* 画图分析反射的原理
		* 首先需要把java文件保存到本地硬盘 .java
		* 编译java文件，成.class文件
		* 使用jvm，把class文件通过类加载加载到内存中
		* 万事万物都是对象，class文件在内存中使用Class类表示

		* 当使用反射时候，首先需要获取到Class类，得到了这个类之后，就可以得到class文件里面的所有内容
			- 包含属性  构造方法 普通方法
		* 属性通过一个类 Filed
		* 构造方法通过一个类 Constructor
		* 普通方法通过一个类 Method

16、使用反射操作类里面的无参数的构造方法（**会写**）
* 首先获取到Class类
- // 获取Class类
Class clazz1 = Person.class;
Class clazz2 = new Person().getClass();
Class clazz3 = Class.forName("cn.itcast.test09.Person");

	* 比如： 要对一个类进行实例化，可以new，不使用new，怎么获取？
		- //得到Class
		Class c3 = Class.forName("cn.itcast.test09.Person");
		//得到Person类的实例
		Person p = (Person) c3.newInstance();
	* 代码
	//操作无参数的构造方法
	@Test
	public void test1() throws Exception {
		//得到Class
		Class c3 = Class.forName("cn.itcast.test09.Person");
		//得到Person类的实例
		Person p = (Person) c3.newInstance();
		//设置值
		p.setName("zhangsan");
		System.out.println(p.getName());
	}

17、使用反射操作有参数的构造方法（**会写**）
//操作有参数的构造方法
@Test
public void test2() throws Exception {
//得到Class
Class c1 = Class.forName("cn.itcast.test09.Person");
//使用有参数的构造方法
//c1.getConstructors();//获取所有的构造方法
//传递是有参数的构造方法里面参数类型，类型使用class形式传递
Constructor cs = c1.getConstructor(String.class,String.class);
//通过有参数的构造方法设置值
//通过有参数的构造方法创建Person实例
Person p1 = (Person) cs.newInstance("lisi","100");
System.out.println(p1.getId()+" "+p1.getName());
}

18、使用反射操作属性（**会写**）
* //操作name属性
@Test
public void test3() {
try {
//得到Class类
Class c2 = Class.forName("cn.itcast.test09.Person");
//得到name属性
//c2.getDeclaredFields();//表示得到所有的属性
//得到Person类的实例
Person p11 = (Person) c2.newInstance();
//通过这个方法得到属性，参数是属性的名称
Field f1 = c2.getDeclaredField("name");
//操作的是私有的属性，不让操作，需要设置可以操作私有属性setAccessible(true),可以操作私有属性
f1.setAccessible(true);
//设置name值 set方法，两个参数：第一个参数类的实例，第二个参数是设置的值
f1.set(p11, "wangwu"); //相当于 在 p.name = "wangwu";
System.out.println(f1.get(p11)); //相当于 p.name
}catch(Exception e) {
e.printStackTrace();
}
}

19、使用反射操作普通方法（**会写**）
* 使用Method类表示普通方法
* 代码
//操作普通方法 ，比如操作 setName
@Test
public void test4() throws Exception {
//得到Class类
Class c4 = Class.forName("cn.itcast.test09.Person");
//得到Person实例
Person p4 = (Person) c4.newInstance();
//得到普通方法
//c4.getDeclaredMethods();//得到所有的普通方法
//传递两个参数：第一个参数，方法名称；第二个参数，方法里面参数的类型
Method m1 = c4.getDeclaredMethod("setName", String.class);
//让setName方法执行 ，执行设置值
//使用invoke(p4, "niuqi");传递两个参数：第一个参数，person实例；第二个参数，设置的值
//执行了invoke方法之后，相当于，执行了setName方法，同时通过这个方法设置了一个值是niuqi
m1.invoke(p4, "niuqi");
System.out.println(p4.getName());
}

	* //操作的私有的方法 ，需要设置值是true
	* //m1.setAccessible(true);

	* 当操作的方法是静态的方法时候，因为静态方法调用方式是 类名.方法名，不需要类的实例
	* 使用反射操作静态方式时候，也是不需要实例
	* 在invokie方法的第一个参数里面，写一个 null
		- m1.invoke(null, "niuqi");