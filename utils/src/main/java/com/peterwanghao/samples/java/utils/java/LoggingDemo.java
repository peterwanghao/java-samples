package com.peterwanghao.samples.java.utils.java;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggingDemo {
	public static void main(String[] args){
        Logger myLogger = Logger.getLogger("com.peterwanghao.LoggingDemo");

        myLogger.setLevel(Level.ALL);
        
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        myLogger.addHandler(consoleHandler);
        
        FileHandler fileHandler = null;
        try {
			String filePath = "E:\\" + File.separator + "logs";
			String logPath = "LoggingDemo.log";
			File fi = new File(filePath);
			if ((fi.exists()) && (fi.isDirectory())) {
				logPath = filePath + File.separator + logPath;
			} else if (!fi.exists()) {
				try {
					fi.mkdirs();
					logPath = filePath + File.separator + logPath;
				} catch (Exception localException) {
				}
			}
			fileHandler = new FileHandler(logPath, true);//true表示日志内容在文件中追加
			fileHandler.setLevel(Level.ALL);//级别为ALL，记录所有消息
			fileHandler.setFormatter(new Formatter() {
				private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				public String format(LogRecord record) {
					StringBuilder sb = new StringBuilder();
					String dataFormat = this.sdf.format(Long.valueOf(record.getMillis()));
					sb.append(dataFormat).append(" ");
					sb.append("level:").append(record.getLevel()).append(" ");
					sb.append(record.getMessage()).append("\n");

					return sb.toString();
				}
			});
			myLogger.addHandler(fileHandler);
			
		} catch (Throwable e) {
			System.out.println("创建文件失败！" + e.getMessage());
		}
        
        myLogger.log(Level.SEVERE, "严重信息");
        myLogger.log(Level.WARNING, "警告信息");
        myLogger.log(Level.INFO, "一般信息");
        myLogger.log(Level.CONFIG, "设定方面的信息");
        myLogger.log(Level.FINE, "细微的信息");
        myLogger.log(Level.FINER, "更细微的信息");
        myLogger.log(Level.FINEST, "最细微的信息");
    }
}
