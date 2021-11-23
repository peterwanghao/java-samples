package com.peterwanghao.samples.java.utils.java;

public class BuildCar {

	public static void main(String[] args) {

		// 第一种方式
		ICar iCar1 = new ICar() {
			@Override
			public Car getCar(String name, Integer money) {
				return new Car(name, money);
			}
		};
		System.out.println(iCar1.getCar("凯迪拉克", 30));

		// 第二种方式
		ICar iCar2 = (name, money) -> new Car(name, money);
		Car car = iCar2.getCar("凯迪拉克", 30);
		System.out.println(car.toString());

		// 第三种方式
		ICar iCar3 = Car::new;
		Car car3 = iCar3.getCar("凯迪拉克", 30);
		System.out.println(car3);
	}
}

@FunctionalInterface
interface ICar {

	Car getCar(String name, Integer money);

}

class Car {

	private String name;

	private Integer money;

	public Car(String name, Integer money) {
		this.name = name;
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "Car{" + 
				"name='" + name + '\'' + 
				", money=" + money + '}';
	}
}