package com.peterwanghao.samples.java.utils.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolatileVariable {
	private volatile int count = 0;  
    
    public void increment() {
        count++;
    }
    
    public synchronized  void incrementSync() {
        count++;
    }
    
    private int getCount() {
        return count;
    }
    
    private void setCount(int count) {
        this.count = count;
    }
    
    /** 
     * 这里模拟一个递增的任务，递增目标为50000 
     */  
    public static void main(String[] args) throws InterruptedException {  
        final VolatileVariable counter = new VolatileVariable();  
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
        
        counter.setCount(0);
        ExecutorService executor2 = Executors.newFixedThreadPool(10);
        start = System.currentTimeMillis();  
        for (int i = 0; i < workCount; i++) {  
            Runnable runnable = new Runnable() {  
                @Override  
                public void run() {  
                    counter.incrementSync();  
                }  
            };  
            executor2.execute(runnable);  
        }  
        // 关闭启动线程，执行未完成的任务  
        executor2.shutdown();  
        // 等待所有线程完成任务，完成后才继续执行下一步  
        executor2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);  
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");  
        System.out.println("执行结果：count=" + counter.getCount()); 
    }
}
