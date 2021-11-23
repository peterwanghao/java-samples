package com.peterwanghao.samples.java.utils.java;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BuildBicycle {
	public static void main(String[] args) {

		// 第一种方式 使用new关键字
		Bicycle bicycle = new Bicycle();
		bicycle.setName("永久");
		bicycle.setMoney(500);
		System.out.println(bicycle);

		// 第二种方式 使用匿名类
		IBicycle bicycle1 = new IBicycle() {
			@Override
			public Bicycle getBicycle(String name, Integer money) {
				Bicycle bicycle = new Bicycle();
				bicycle.setName(name);
				bicycle.setMoney(money);
				return bicycle;
			}
		};
		System.out.println(bicycle1.getBicycle("永久", 500));

		// 第三种方式 Lambda表达式
		IBicycle IBicycle2 = (name, money) -> {
			new Bicycle();
			bicycle.setName(name);
			bicycle.setMoney(money);
			return bicycle;
		};
		Bicycle bicycle2 = IBicycle2.getBicycle("永久", 500);
		System.out.println(bicycle2);
		
		// 第四种方式 类名::new Lambda表达式一种
		// 构造方法引用
		IBicycle IBicycle3 = Bicycle::new;
		Bicycle bicycle3 = IBicycle3.getBicycle("永久", 500);
		System.out.println(bicycle3);
		
		// 构造方法引用 Supplier接口 无参数
		Supplier<Bicycle> IBicycle4 = Bicycle::new;
		Bicycle bicycle4 = IBicycle4.get();
		bicycle4.setName("永久");
		bicycle4.setMoney(500);
		System.out.println(bicycle4);
		
		// 构造方法引用 BiFunction接口 两个参数
		BiFunction<String, Integer, Bicycle> IBicycle5 = Bicycle::new;
		Bicycle bicycle5 = IBicycle5.apply("永久", 500);
		System.out.println(bicycle5);
		
	}
}
