package com.peterwanghao.samples.java.utils;

/**
 * @ClassName: StringUtil
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年9月26日 下午1:57:53
 * @version V1.0
 * 
 */
public class StringUtil {

	public static void equal1(String i, String j) {
		try {
			if (i == j) {
				System.out.println("equal1相等");
			}
		} catch (Exception ex) {
			System.out.println("equal1 异常：");
			ex.printStackTrace();
		} finally {
			System.out.println("equal1结束");
		}

	}

	public static void equal2(String i, String j) {
		try {
			if(i.equals(j)) {
				System.out.println("equal2相等");
			}
		} catch (Exception ex) {
			System.out.println("equal2 异常：");
			ex.printStackTrace();
		} finally {
			System.out.println("equal2结束");
		}
	}

	public static void equal3(String i, String j) {
		try {
			if(j.equals(i)) {
				System.out.println("equal3相等");
			}
			if (i instanceof String) {
				System.out.println("is String");
			}else {
				System.out.println("is Not String");
			}
		} catch (Exception ex) {
			System.out.println("equal3异常：");
			ex.printStackTrace();
		} finally {
			System.out.println("equal3结束");
		}
	}
	
	public static void main(String[] args) {
		equal1(null,"abc");
		equal2(null,"abc");
		equal3(null,"abc");
    }
}
