package com.peterwanghao.samples.java.utils.java;

import lombok.Data;

@Data
public class Emp {
	private String address;

	private String name;

	private Integer age;
	
	private Double salary;

	public Emp() {

	}

	public Emp(String address) {
		this.address = address;
	}

	public Emp(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public Emp(String address, String name, Integer age) {
		this.address = address;
		this.name = name;
		this.age = age;
	}
	
	public Emp(String address, String name, Integer age, Double salary) {
		this.address = address;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
}
