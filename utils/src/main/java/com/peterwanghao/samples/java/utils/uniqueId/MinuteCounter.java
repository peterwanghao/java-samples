package com.peterwanghao.samples.java.utils.uniqueId;

import java.util.concurrent.atomic.AtomicInteger;

/**   
 * @ClassName:  MinuteCounter
 * @Description:分钟计数器
 * @author: wanghao
 * @date:   2019年12月13日 下午9:26:13
 * @version V1.0
 * 
 */
public class MinuteCounter {
	private static final int MASK = 0x7FFFFFFF;
    private final AtomicInteger atom;
    
    public MinuteCounter() {
        atom = new AtomicInteger(0);
    }
    
    public final int incrementAndGet() {
        return atom.incrementAndGet() & MASK;
    }
    
    public int get() {
        return atom.get() & MASK;
    }
    
    public void set(int newValue) {
    	atom.set(newValue & MASK);
    }
}
