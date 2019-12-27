package com.peterwanghao.samples.java.utils.java;

/**   
 * @ClassName:  Test1
 * @Description:通过匿名内部类实现功能接口
 * @author: wanghao
 * @date:   2019年12月27日 上午11:28:31
 * @version V1.0
 * 
 */
public class Test1 {
	public static void main(String[] args) {
		Animal animal = new Animal() {

			@Override
			public void eat() {
				System.out.println("Anonymous class implementing eat()");
			}

		};

		animal.eat();
	}
}
