package com.peterwanghao.samples.java.utils.uniqueId;

import java.util.UUID;

/**
 * @ClassName: GenerateUUID
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年12月17日 下午4:05:28
 * @version V1.0
 * 
 */
public class GenerateUUID {
	public static final void main(String... args) {
		// generate random UUIDs
		UUID idOne = UUID.randomUUID();
		UUID idTwo = UUID.randomUUID();
		log("UUID One: " + idOne);
		log("UUID Two: " + idTwo);
	}

	private static void log(Object object) {
		System.out.println(String.valueOf(object));
	}
}
