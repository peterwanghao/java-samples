package com.peterwanghao.samples.java.utils.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorCounter {

    //这里使用LongAccumulator类
    public LongAccumulator count = new LongAccumulator((a,b)->a+b, 0);
    //public LongAccumulator count = new LongAccumulator((a,b)->a*b, 1);
    
    public void increment() {
		// 自增
		count.accumulate(1);
	}

    /**
	 * 获取当前的值
	 * 
	 * @return
	 */
	public long getCount() {
		return count.get();
	}
	
    /**
	 * 这里模拟一个递增的任务，递增目标为50000
	 */
	public static void main(String[] args) throws InterruptedException {
		final LongAccumulatorCounter counter = new LongAccumulatorCounter();
		int workCount = 50000;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		long start = System.currentTimeMillis();
		for (int i = 0; i < workCount; i++) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					counter.increment();
				}
			};
			executor.execute(runnable);
		}
		// 关闭启动线程，执行未完成的任务
		executor.shutdown();
		// 等待所有线程完成任务，完成后才继续执行下一步
		executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
		System.out.println("执行结果：count=" + counter.getCount());
	}
}