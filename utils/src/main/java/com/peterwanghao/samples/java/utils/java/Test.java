package com.peterwanghao.samples.java.utils.java;

/**   
 * @ClassName:  Test
 * @Description:通过实现类调用功能接口
 * @author: wanghao
 * @date:   2019年12月27日 上午11:27:56
 * @version V1.0
 * 
 */
public class Test {
	public static void main(String[] args) {
		Animal animal = new SeaAnimal();
		animal.eat();
	}
}
