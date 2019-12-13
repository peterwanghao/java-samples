package com.peterwanghao.samples.java.utils.uniqueId;

import java.util.Arrays;

/**   
 * @ClassName:  ByteToBit
 * @Description:将Byte转为Bit
 * @author: wanghao
 * @date:   2019年12月13日 上午10:16:03
 * @version V1.0
 * 
 */
public class ByteToBit {
	/** 
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit 
     */  
    public static byte[] getBooleanArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  
    /** 
     * 把byte转为字符串的bit 
     */  
    public static String byteToBit(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }
    
    public static void main(String[] args) {  
        byte b = 0x35; // 0011 0101  
        // 输出 [0, 0, 1, 1, 0, 1, 0, 1]  
        System.out.println(Arrays.toString(getBooleanArray(b)));  
        // 输出 00110101  
        System.out.println(byteToBit(b));  
        // JDK自带的方法，会忽略前面的 0  
        System.out.println(Integer.toBinaryString(0x35));  
    }
}
