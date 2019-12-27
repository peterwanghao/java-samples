package com.peterwanghao.samples.java.utils.java;

/**   
 * @ClassName:  Test2
 * @Description:通过Lambda表达式实现功能接口
 * @author: wanghao
 * @date:   2019年12月27日 上午11:29:03
 * @version V1.0
 * 
 */
public class Test2 {
	public static void main(String[] args) {
		Animal animal = ()->System.out.println("Lambda Expression implementing eat()");	
		animal.eat();
	}
}
