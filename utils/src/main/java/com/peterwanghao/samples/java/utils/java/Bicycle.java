package com.peterwanghao.samples.java.utils.java;

import lombok.Data;

@Data
public class Bicycle {
	public Bicycle() {

	}
	
	public Bicycle(String name, Integer money) {
		this.name = name;
		this.money = money;
	}
	
	private String name;

	private Integer money;

}
