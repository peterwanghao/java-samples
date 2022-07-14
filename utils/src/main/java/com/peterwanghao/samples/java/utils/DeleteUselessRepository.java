package com.peterwanghao.samples.java.utils;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

/**  
* @Title: DeleteUselessRepository.java
* @Package com.peterwanghao.samples.java.utils
* @Description: Clean Maven Repository, delete useless files.
* @author wanghao  
* @date 2022年7月14日 下午3:29:44
* @version V1.0  
*/
public class DeleteUselessRepository {
	//本地maven仓库的地址
	private static String MAVEN_PATH = "E:\\opensource\\repository";

    private static long total;
    private static long time;

    public static void main(String[] args) {
        File dir = new File(MAVEN_PATH);
        if (!dir.isDirectory()) {
            System.out.println("请输入Maven仓库所在路径");
            System.exit(0);
        } else {
            Instant startNow = Instant.now();
            DeleteUselessRepository.delFile(dir);
            Instant endNow = Instant.now();
            time = Duration.between(startNow, endNow).toMillis();
        }
        System.out.printf("删除数量：%d 耗时：%d 毫秒", total, time);
    }

    public static void delFile(File file) {
        File[] list = file.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                delFile(f);
                if (f.getName().equals("unknown")) {
                    delAll(f);
                    total++;
                    System.out.println("删除：" + f.getAbsolutePath());
                } else if (f.getName().startsWith("${") && f.getName().endsWith("}")) {
                    // 如果 文件夹名称是以 ${ 开头 } 结尾，那么将这个文件夹及其下面所有文件全部删除
                    delAll(f);
                    f.delete();
                    total++;
                    System.out.println("删除：" + f.getAbsolutePath());
                } else if (f.listFiles().length == 0) {
                    // 删除空文件夹
                    f.delete();
                    total++;
                    System.out.println("删除：" + f.getAbsolutePath());
                }
            } else {
                if (f.getName().endsWith(".lastUpdated")) {
                    f.delete();
                    total++;
                    System.out.println("删除：" + f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 删除文件夹下的所有文件夹、文件及其子文件夹、文件
     *
     * @param file file
     */
    public static void delAll(File file) {
        File[] list = file.listFiles();
        for (File f : list) {
            if (f.isFile()) {
                // 是文件就删除
                f.delete();
                total++;
                System.out.println("删除：" + f.getAbsolutePath());
            } else {
                // 先将文件夹下的文件夹和文件全部删除再删除源文件夹
                delAll(f);
                f.delete();
                total++;
                System.out.println("删除：" + f.getAbsolutePath());
            }
        }
    }

}
