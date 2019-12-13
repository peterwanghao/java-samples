package com.peterwanghao.samples.java.utils.uniqueId;

/**   
 * @ClassName:  SnowflakeIdWorker3rd
 * @Description:snowflake算法改进
 * @author: wanghao
 * @date:   2019年12月13日 下午12:50:47
 * @version V1.0
 * 
 * 将产生的Id类型更改为Integer 32bit <br>
 * 把时间戳的单位改为分钟,使用25个比特的时间戳（分钟） <br>
 * 去掉机器ID和数据中心ID <比如>
 * 7个比特作为自增值，即2的7次方等于128。
 */
public class SnowflakeIdWorker3rd {
	/** 开始时间截 (2019-01-01) */
	private final long twepoch = 1546272000000L;

	/** 机器id所占的位数 */
	//private final long workerIdBits = 5L;

	/** 数据标识id所占的位数 */
	//private final long datacenterIdBits = 5L;

	/** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
	//private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

	/** 支持的最大数据标识id，结果是31 */
	//private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

	/** 序列在id中占的位数 */
	private final long sequenceBits = 7L;

	/** 机器ID向左移22位 */
	//private final long workerIdShift = sequenceBits;

	/** 数据标识id向左移27位(22+5) */
	//private final long datacenterIdShift = sequenceBits + workerIdBits;

	/** 时间截向左移7位 */
	private final long timestampLeftShift = sequenceBits;

	/** 生成序列的掩码，这里为127 */
	private final int sequenceMask = -1 ^ (-1 << sequenceBits);

	/** 工作机器ID(0~31) */
	//private long workerId;

	/** 数据中心ID(0~31) */
	//private long datacenterId;

	/** 分钟内序列(0~4194303) */
	private int sequence = 0;

	/** 上次生成ID的时间截 */
	private int lastTimestamp = -1;

	// ==============================Constructors=====================================
	/**
	 * 构造函数
	 * 
	 * @param workerId
	 *            工作ID (0~31)
	 * @param datacenterId
	 *            数据中心ID (0~31)
	 */
	public SnowflakeIdWorker3rd() {
		
	}

	// ==============================Methods==========================================
	/**
	 * 获得下一个ID (该方法是线程安全的)
	 * 
	 * @return SnowflakeId
	 */
	public synchronized long nextId() {
		int timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds",
					lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		// 时间戳改变，毫秒内序列重置
		else {
			sequence = 0;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;

		// 移位并通过或运算拼到一起组成32位的ID
		return ((timestamp - twepoch) << timestampLeftShift) //
				| sequence;
	}

	/**
	 * 阻塞到下一个分钟，直到获得新的时间戳
	 * 
	 * @param lastTimestamp
	 *            上次生成ID的时间截
	 * @return 当前时间戳
	 */
	protected int tilNextMillis(int lastTimestamp) {
		int timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 返回以分钟为单位的当前时间
	 * 
	 * @return 当前时间(分钟)
	 */
	protected int timeGen() {
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000 / 60);
		return Integer.valueOf(timestamp);
	}

	// ==============================Test=============================================
	/** 测试 */
	public static void main(String[] args) {
		SnowflakeIdWorker3rd idWorker = new SnowflakeIdWorker3rd();
		for (int i = 0; i < 1000; i++) {
			long id = idWorker.nextId();
			System.out.println(i+": "+Long.toBinaryString(id));//转为Bit，前面的0省略掉
			System.out.println(id & 0x7FFFFFFF);
		}
	}
}
