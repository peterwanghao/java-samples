package com.peterwanghao.samples.java.utils.java;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class OptionalDemo {

	public String getUserSteetName(User user) {

		if (null != user) {
			Address address = user.getAddress();
			if (null != address) {
				Street street = address.getStreet();
				if (null != street) {
					return street.getStreetName();
				}
			}
		}

		return "nothing found";
	}

	/***
	 * 原方法明显的问题是if判断层级太深，下面复用Optional改写
	 * 
	 * @param user
	 * @return
	 */
	public String getUserSteetNameBetter(User user) {

		Optional<User> userOptional = Optional.ofNullable(user);
		final String streetName = Optional.ofNullable(
				Optional.ofNullable(userOptional
				.orElse(new User()).getAddress())
				.orElse(new Address()).getStreet())
				.orElse(new Street()).getStreetName();
		return StringUtils.isEmpty(streetName) ? "nothing found" : streetName;
	}

	public static void main(String[] args) {
		OptionalDemo demo = new OptionalDemo();
		System.out.println(demo.getUserSteetName(null));
		System.out.println(demo.getUserSteetNameBetter(null));
		
		Street street = new Street();
		street.setStreetName("等等");
		street.setStreetNo(123);
		
		Address address = new Address();
		address.setStreet(street);
		
		User user = new User();
		user.setAddress(address);
		user.setName("张三");
		user.setAge(12);
		
		System.out.println(demo.getUserSteetName(user));
		System.out.println(demo.getUserSteetNameBetter(user));
	}
}
