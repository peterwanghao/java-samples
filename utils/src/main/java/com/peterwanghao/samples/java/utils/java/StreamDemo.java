package com.peterwanghao.samples.java.utils.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamDemo {
	public static void main(String args[]) {
		log.info("创建流的几种方式:");
		String[] dd = { "a", "b", "c", "b" };
		// 方法1
		Arrays.stream(dd).forEach(System.out::print);// abc
		System.out.println();
		
		// 方法2
		Stream.of(dd).forEach(System.out::print);// abc
		System.out.println();
		
		// 方法3
		Arrays.asList(dd).stream().forEach(System.out::print);// abc
		System.out.println();
		
		// 方法4
		Stream.iterate(0, x -> x + 1).limit(10).forEach(System.out::print);// 0123456789
		System.out.println();
		
		// 方法5
		Stream.generate(() -> "x").limit(10).forEach(System.out::print);// xxxxxxxxxx
		System.out.println();
		
		log.info("{}", buildList(100));
		log.info("{}", buildIterate(100));
		
		log.info("中间操作:");
		Stream<String> stream = Arrays.stream(dd);
		stream.filter(str -> str.equals("b")).forEach(System.out::print);
		System.out.println();
		
		Integer[] intArray = { 1, 2, 3 };
		Stream<Integer> stream2 = Arrays.stream(intArray);
		stream2.map(str -> Integer.toString(str)).forEach(str -> {
			System.out.println(str);// 1 ,2 ,3
			System.out.println(str.getClass());// class java.lang.String
		});

		String[] strs = { "aaa", "bbb", "ccc" };
		Arrays.stream(strs).map(str -> str.split("")).forEach(System.out::println);// Ljava.lang.String;@53d8d10a
		Arrays.stream(strs).map(str -> str.split("")).flatMap(Arrays::stream).forEach(System.out::print);// aaabbbccc
		System.out.println();
		Arrays.stream(strs).map(str -> str.split("")).flatMap(str -> Arrays.stream(str)).forEach(System.out::print);// aaabbbccc
		System.out.println();
		
		// 对数组流，先过滤重复，在排序，再控制台输出 1，2，3
		Arrays.asList(3, 1, 2, 1).stream().distinct().sorted().forEach(str -> {
			System.out.println(str);
		});
		// 对list里的emp对象，取出薪水，并对薪水进行排序，然后输出薪水的内容，map操作，改变了Strenm的泛型对象
		list.stream().map(emp -> emp.getSalary()).sorted().forEach(salary -> {
			System.out.println(salary);
		});
		// 根据emp的属性name，进行排序
		println(list.stream().sorted(Comparator.comparing(Emp::getName)));
 
		// 给年纪大于30岁的人，薪水提升1.5倍，并输出结果
		Stream<Emp> streamEmp = list.stream().filter(emp -> {
			return emp.getAge() > 30;
		}).peek(emp -> {
			emp.setSalary(emp.getSalary() * 1.5);
		});
		println(streamEmp);
		// 数字从1开始迭代（无限流），下一个数字，是上个数字+1，忽略前5个 ，并且只取10个数字
		// 原本1-无限，忽略前5个，就是1-5数字，不要，从6开始，截取10个，就是6-15
		Stream.iterate(1, x -> ++x).skip(5).limit(10).forEach(System.out::println);
		
		log.info("终端操作:");
		List<Integer> numbers = Stream.iterate(1, x -> x + 1).limit(10).collect(Collectors.toList());
        Integer aa = 0;
        for (Integer i : numbers) {
            aa += i;
        }
        log.info("{}", aa);
        
        Optional<Integer> bb = numbers.stream().reduce((a, b) -> a + b);
        log.info("{}", bb.get());
        
        Integer cc = numbers.stream().reduce(0, (a, b) -> a + b);
        log.info("{}", cc);
        
        // 第三个参数定义的规则并没有执行。这是因为reduce的第三个参数是在使用parallelStream的reduce操作时，合并各个流结果的，
        // 本例中使用的是stream，所以第三个参数是不起作用的
        Integer ee = numbers.stream().reduce(0, (a, b) -> a + b, (a, b) -> a - b);
        log.info("{}", ee);
        
        // 转list
        List<String> names = list.stream().map(emp -> emp.getName()).collect(Collectors.toList());
        log.info("{}", names);
        // 转set
        Set<String> address = list.stream().map(emp -> emp.getName()).collect(Collectors.toSet());
        log.info("{}", address);
        // 转map，需要指定key和value，Function.identity()表示当前的Emp对象本身
        Map<String, Emp> map = list.stream().collect(Collectors.toMap(Emp::getName, Function.identity()));
        log.info("{}", map);
        // 计算元素中的个数
        Long count = list.stream().collect(Collectors.counting());
        log.info("{}", count);
        // 数据求和 summingInt summingLong，summingDouble
        Integer sumAges = list.stream().collect(Collectors.summingInt(Emp::getAge));
        log.info("{}", sumAges);
        // 平均值 averagingInt,averagingDouble,averagingLong
        Double aveAges = list.stream().collect(Collectors.averagingInt(Emp::getAge));
        log.info("{}", aveAges);
 
        // 综合处理的，求最大值，最小值，平均值，求和操作
        // summarizingInt，summarizingLong,summarizingDouble
        IntSummaryStatistics intSummary = list.stream().collect(Collectors.summarizingInt(Emp::getAge));
        System.out.println(intSummary.getAverage());// 19.5
        System.out.println(intSummary.getMax());// 22
        System.out.println(intSummary.getMin());// 17
        System.out.println(intSummary.getSum());// 117
 
        // 连接字符串,当然也可以使用重载的方法，加上一些前缀，后缀和中间分隔符
        String strEmp = list.stream().map(emp -> emp.getName()).collect(Collectors.joining());
        String strEmp1 = list.stream().map(emp -> emp.getName()).collect(Collectors.joining("-中间的分隔符-"));
        String strEmp2 = list.stream().map(emp -> emp.getName()).collect(Collectors.joining("-中间的分隔符-", "前缀*", "&后缀"));
        System.out.println(strEmp);// 小名小红小蓝小灰小黄小白
        // 小名-中间的分隔符-小红-中间的分隔符-小蓝-中间的分隔符-小灰-中间的分隔符-小黄-中间的分隔符-小白
        System.out.println(strEmp1);
        // 前缀*小名-中间的分隔符-小红-中间的分隔符-小蓝-中间的分隔符-小灰-中间的分隔符-小黄-中间的分隔符-小白&后缀
        System.out.println(strEmp2);
        // maxBy 按照比较器中的比较结果刷选 最大值
        Optional<Integer> maxAge = list.stream().map(emp -> emp.getAge())
                .collect(Collectors.maxBy(Comparator.comparing(Function.identity())));
        // 最小值
        Optional<Integer> minAge = list.stream().map(emp -> emp.getAge())
                .collect(Collectors.minBy(Comparator.comparing(Function.identity())));
        log.info("max:{}", maxAge);
        log.info("min:{}", minAge);
 
        // 归约操作
        list.stream().map(emp -> emp.getAge()).collect(Collectors.reducing((x, y) -> x + y));
        list.stream().map(emp -> emp.getAge()).collect(Collectors.reducing(0, (x, y) -> x + y));
        // 分操作 groupingBy 根据地址，把原list进行分组
        Map<String, List<Emp>> mapGroup = list.stream().collect(Collectors.groupingBy(Emp::getAddress));
        log.info("{}", mapGroup);
        // partitioningBy 分区操作 需要根据类型指定判断分区
        Map<Boolean, List<Integer>> partitioningMap = list.stream().map(emp -> emp.getAge())
                .collect(Collectors.partitioningBy(emp -> emp > 20));
        log.info("{}", partitioningMap);
        
	}
	
	public static List<Integer> buildList(final int size) {
		List<Integer> list = new ArrayList<>(size);
		for (int i = 1; i <= size; i++) {
			list.add(i);
		}
		return list;
	}
 
	public static List<Integer> buildIterate(final int size) {
		return Stream.iterate(1, x -> ++x).limit(size).collect(Collectors.toList());
	}

	public static List<Emp> list = new ArrayList<>();
	static {
		list.add(new Emp("北京", "xiaoHong1", 20, 1000.0));
		list.add(new Emp("北京", "xiaoHong2", 25, 2000.0));
		list.add(new Emp("北京", "xiaoHong3", 30, 3000.0));
		list.add(new Emp("北京", "xiaoHong4", 35, 4000.0));
		list.add(new Emp("北京", "xiaoHong5", 38, 5000.0));
		list.add(new Emp("北京", "xiaoHong6", 45, 9000.0));
		list.add(new Emp("北京", "xiaoHong7", 55, 10000.0));
		list.add(new Emp("北京", "xiaoHong8", 42, 15000.0));
	}

	public static void println(Stream<Emp> stream) {
		stream.forEach(emp -> {
			System.out.println(String.format("名字：%s，年纪：%s，薪水：%s", emp.getName(), emp.getAge(), emp.getSalary()));
		});
	}

}
