package com.peterwanghao.samples.java.hoverfly.service.controller;

/**   
 * @ClassName:  HoverflyServiceResponse
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年1月17日 下午2:49:01
 * @version V1.0
 * 
 */
public class HoverflyServiceResponse {
	private String message;
	private String responseTime;
	private String transactionid;

	public HoverflyServiceResponse(String message, String responseTime, String transactionid) {
		super();
		this.message = message;
		this.responseTime = responseTime;
		this.transactionid = transactionid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
}
