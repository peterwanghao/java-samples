package com.peterwanghao.samples.java.utils.uniqueId;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: TimestampUtil
 * @Description:取时间戳的工具类
 * @author: wanghao
 * @date: 2019年12月13日 上午10:18:32
 * @version V1.0
 * 
 *          时间戳（毫秒） System.currentTimeMillis() <br>
 *          时间戳（转秒） System.currentTimeMillis() / 1000 <br>
 *          时间戳（转分钟） System.currentTimeMillis() / 1000 / 60 <br>
 *          时间戳（转小时） System.currentTimeMillis() / 1000 / (60 * 60) <br>
 *          时间戳（转天） System.currentTimeMillis() / 1000 / (60 * 60 * 24)
 */
public class TimestampUtil {

	// int转为byte数组
	public static byte[] intToByteArray(int a) {
		return new byte[]{(byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF),
				(byte) ((a >> 8) & 0xFF), (byte) (a & 0xFF)};
	}

	// long转为byte数组
	private static ByteBuffer buffer = ByteBuffer.allocate(8);
	public static byte[] longToBytes(long x) {
		buffer.putLong(0, x);
		return buffer.array();
	}

	/**
	 * 获取精确到秒的时间戳
	 * 
	 * @return
	 */
	public static int getSecondTimestamp(Date date) {
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime() / 1000);
		return Integer.valueOf(timestamp);
	}

	/**
	 * 获取精确到分钟的时间戳
	 * 
	 * @return
	 */
	public static int getMinuteTimestamp(Date date) {
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime() / 1000 / 60);
		return Integer.valueOf(timestamp);
	}

	public static void main(String[] args) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date date = format.parse("2019-01-01");

			cal.setTime(date);
			long base = cal.getTimeInMillis();
			System.out.println("base : "+base);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("A------------------------------");
		byte[] a = intToByteArray(512);
		System.out.println(a.length);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
			System.out
					.println(Arrays.toString(ByteToBit.getBooleanArray(a[i])));
		}

		System.out.println("B------------------------------");

		long t = System.currentTimeMillis();
		byte[] b = longToBytes(t);
		System.out.println(b.length);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
			System.out
					.println(Arrays.toString(ByteToBit.getBooleanArray(b[i])));
		}

		System.out.println("C------------------------------");
		int sec = getSecondTimestamp(new Date());
		System.out.println("sec : " + sec);

		byte[] c = intToByteArray(sec);
		System.out.println(c.length);
		for (int i = 0; i < c.length; i++) {
			System.out.println(c[i]);
			System.out
					.println(Arrays.toString(ByteToBit.getBooleanArray(c[i])));
		}

		System.out.println("D------------------------------");
		int minute = getMinuteTimestamp(new Date());
		System.out.println("minute : " + minute);

		byte[] d = intToByteArray(minute);
		System.out.println(d.length);
		for (int i = 0; i < d.length; i++) {
			System.out.println(d[i]);
			System.out
					.println(Arrays.toString(ByteToBit.getBooleanArray(d[i])));
		}
	}
}
