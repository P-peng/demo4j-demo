package com.ge.entity;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author dengzhipeng
 * @date 2019/06/14
 */
@Setter
@Getter
public class ClassInfo {
    /**
     * 基本包
     */
    private String basePackage;
    /**
     * 大写类名
     */
    private String bigClassName;
    /**
     * 首字母小写的类名
     */
    private String smallClassName;
    private LinkedHashMap<String, String> fieldMap = new LinkedHashMap();
    private List<String> genericFieldList = new ArrayList();
    private LinkedHashMap<String, String> importFieldMap = new LinkedHashMap();
    private List<AssociationObject> association = new ArrayList();

    private String insert;
    private String delete;
    private String update;
    private String selectByPrimaryKey;
    private String selectAll;
    private String queryForCount;
    private String queryListData;
    private String limit;

    public ClassInfo(){}

    public ClassInfo(Class<?> clazz) throws Exception {
        String pkgName = clazz.getPackage().getName();
        this.basePackage = pkgName.substring(0, pkgName.lastIndexOf("."));
        this.bigClassName = clazz.getSimpleName();
        this.smallClassName = this.bigClassName.substring(0, 1).toLowerCase() + this.bigClassName.substring(1);
        this.getObjectFiled(clazz);
    }

    public void getObjectFiled(Class<?> clazz) throws Exception {
        // 创建父类对象
        Object currentObject = clazz.getDeclaredConstructor().newInstance();
//        if (currentObject instanceof BaseDomain) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // java中的修饰码 如public 1 private 2 protect 4 static 8
                if (field.getModifiers() < 8) {
                    // 集合类型的不添加
                    if (!field.getType().getName().endsWith("List") && !field.getType().getName().endsWith("Map")) {
                        this.fieldMap.put(field.getName(), field.getType().getName());
                    }
            }
//            }
            // 递归调用父类中的方法
            getObjectFiled(clazz.getSuperclass());
        }
    }
}

