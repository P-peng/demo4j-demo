package com.ge.utils;

import java.io.File;

/**
 * 文件工具类
 * @author dengzhipeng
 * @date 2019/06/13
 */
public class FileUtils {

    /**
     * 检查该地址文件是否存在, 绝对地址相当地址都行
     * @param filePath
     * @return
     */
    public static boolean fileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }
}
