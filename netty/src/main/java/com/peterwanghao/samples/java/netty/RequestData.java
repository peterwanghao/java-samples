package com.peterwanghao.samples.java.netty;

/**
 * @ClassName: RequestData
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年11月22日 下午1:17:13
 * @version V1.0
 * 
 */
public class RequestData {
	private int intValue;
	private String stringValue;

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	@Override
	public String toString() {
		return "RequestData{" + "intValue=" + intValue + ", stringValue='"
				+ stringValue + '\'' + '}';
	}
}