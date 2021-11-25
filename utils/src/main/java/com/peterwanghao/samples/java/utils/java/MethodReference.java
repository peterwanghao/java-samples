package com.peterwanghao.samples.java.utils.java;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class MethodReference {
	public static void main(String args[]) {
		/*************** 方法的引用 ****************/
        // 类::静态方法名
		Comparator<Integer> aa = (x, y) -> Integer.compare(x, y); //Lambda表达式
        System.out.println(aa.compare(3, 2));
        Comparator<Integer> bb = Integer::compare; //方法引用
        System.out.println(bb.compare(3, 2));
        
 
        Comparator<Integer> cc = (x, y) -> x.compareTo(y);
        System.out.println(cc.compare(3, 2));
        Comparator<Integer> dd = Integer::compareTo;
        System.out.println(dd.compare(3, 2));
        
        // 类::实例方法名
        BiPredicate<String, String> bp = (x, y) -> x.equals(y); //Lambda表达式
        System.out.println(bp.test("a", "b"));
        BiPredicate<String, String> bp1 = String::equals; //方法引用
        System.out.println(bp1.test("a", "b"));
 
        // 对象::实例方法名
        Consumer<String> con1 = x -> System.out.println(x); //Lambda表达式
        con1.accept("abc");
        Consumer<String> con = System.out::println; //方法引用
        con.accept("abc");
        
        Emp emp = new Emp("上海", "xiaoMIng", 18);
        Consumer<String> setter = x -> emp.setAddress(x);
        setter.accept("ddd");
        Supplier<String> supper = () -> emp.getAddress();
        System.out.println(supper.get());
        Consumer<String> setter1 = emp::setAddress;
        setter1.accept("eee");
        Supplier<String> supper1 = emp::getAddress;
        System.out.println(supper1.get());
        
        /*************** 构造器的引用 ****************/
        // 无参构造函数，创建实例
        Supplier<Emp> supper2 = () -> new Emp();
        Supplier<Emp> supper3 = Emp::new;
        Emp emp1 = supper3.get();
        Consumer<String> setter2 = emp1::setAddress;
        setter2.accept("eee");
        System.out.println(emp1);
        // 一个参数
        Function<String, Emp> fun = address -> new Emp(address);
        Function<String, Emp> fun1 = Emp::new;
        System.out.println(fun1.apply("beijing"));
        // 两个参数
        BiFunction<String, Integer, Emp> bFun = (name, age) -> new Emp(name, age);
        BiFunction<String, Integer, Emp> bFun1 = Emp::new;
        System.out.println(bFun1.apply("xiaohong", 18));
	}
	
}
