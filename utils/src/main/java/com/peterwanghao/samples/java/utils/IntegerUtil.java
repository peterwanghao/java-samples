package com.peterwanghao.samples.java.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**   
 * @ClassName:  IntegerUtil
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年12月9日 下午5:17:54
 * @version V1.0
 * 
 */
public class IntegerUtil {
	private static final int MASK = 0x7FFFFFFF;
	private final AtomicInteger atom;

	public IntegerUtil() {
        atom = new AtomicInteger(2147483646);
    }
	
	public final int incrementAndGet() {
        return atom.incrementAndGet() & MASK;
    }
	
	public static void main(String[] args) {
		IntegerUtil util = new IntegerUtil();
		for(int i=0; i<8;i++) {
			int aaa = util.incrementAndGet();
			System.out.println("aaa:"+aaa);
		}
	}
}
