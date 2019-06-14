package com.utils;

/**
 * @author dengzhipeng
 * @date 2019/06/14
 */
public class JudgeType {
    public static boolean judgeType(String type) {
        String javaType = "";
        if (type.contains(".")) {
            //类型的截取
            int lastIndexOf = type.lastIndexOf(".");
            javaType = type.substring(lastIndexOf + 1);
        } else {
            javaType = type;
        }
        if ("int".equals(javaType)) {
            return true;
        } else if ("Integer".equals(javaType)) {
            return true;
        } else if ("long".equals(javaType)) {
            return true;
        } else if ("Long".equals(javaType)) {
            return true;
        } else if ("double".equals(javaType)) {
            return true;
        } else if ("Double".equals(javaType)) {
            return true;
        } else if ("String".equals(javaType)) {
            return true;
        } else if ("BigDecimal".equals(javaType)) {
            return true;
        } else if ("Boolean".equals(javaType)) {
            return true;
        } else if ("boolean".equals(javaType)) {
            return true;
        } else if ("Date".equals(javaType)) {
            return true;
        } else {
            return false;
        }
    }
}
