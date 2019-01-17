package com.peterwanghao.samples.java.hoverfly.service.client.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**   
 * @ClassName:  ClientController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date:   2019年1月17日 下午2:57:27
 * @version V1.0
 * 
 */
@RestController
public class ClientController {
	private static final int HOVERFLY_PORT = 8500;
	private static final String HOVERFLY_HOST = "localhost";
	private static final String PROXY = "proxy";
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/invoke")
	public String invoke() {
		System.out.println("inside TestController::invoke()");
		String url = "http://localhost:9080/service/hoverfly";
		String response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
		}).getBody();
		System.out.println("Actual Response : " + response);
		return response;
	}
	
	@Bean
	public RestTemplate restTemplate() {

		String mode = System.getProperty("mode");
		System.out.println("##################### Mode ################# " + mode);

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(HOVERFLY_HOST, HOVERFLY_PORT));
		requestFactory.setProxy(proxy);
		RestTemplate template = null;

		if (mode != null && mode.equalsIgnoreCase(PROXY)) {
			System.out.println("######### Running application in PROXY mode so that we can use simulated hoveryfly server!!!!");
			template = new RestTemplate(requestFactory);
		} else {
			System.out.println("######### Running application in PRODUCTION mode so that we can use simulated hoveryfly server!!!!");
			template = new RestTemplate();
		}

		return template;
	}
}
