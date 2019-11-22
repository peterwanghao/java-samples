package com.peterwanghao.samples.java.netty;

/**
 * @ClassName: ResponseData
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年11月22日 下午1:21:59
 * @version V1.0
 * 
 */
public class ResponseData {
	private int intValue;

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	@Override
	public String toString() {
		return "ResponseData{" + "intValue=" + intValue + '}';
	}
}
