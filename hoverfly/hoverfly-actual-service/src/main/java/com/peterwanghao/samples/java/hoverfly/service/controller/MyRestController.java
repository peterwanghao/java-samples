package com.peterwanghao.samples.java.hoverfly.service.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**   
 * @ClassName:  MyRestController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年1月17日 下午2:44:53
 * @version V1.0
 * 
 */
@RestController
public class MyRestController {
	@RequestMapping(value = "/service/hoverfly")
	public HoverflyServiceResponse getSampleResponse() {
		System.out.println("Inside HoverflyActualServiceApplication::getSampleResponse()");
		return new HoverflyServiceResponse("returned value from HoverflyActualServiceApplication",
				new Date().toString(), UUID.randomUUID().toString());
	}
}
