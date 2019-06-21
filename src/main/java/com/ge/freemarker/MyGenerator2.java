package com.ge.freemarker;

import com.ge.entity.AssociationObject;
import com.ge.entity.ClassInfo;
import com.ge.entity.User;
import com.utils.JudgeType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author dengzhipeng
 * @date 2019/06/14
 */
public class MyGenerator2 {
    /**
     * 统一定义截取字符串的长度为多少字母
     */
    private static final int FEILDL_ENGTH = 1;

    public static void main(String[] args) throws Exception {

        ClassInfo info = new ClassInfo(User.class);

        // 生成xml文件
        createMyBatisXML(info, "src/Mapper2.xml", "src/main/resources/mappers/{0}/{1}Mapper.xml");

    }

    private static void createMyBatisXML(ClassInfo info, String templateFile, String targetFile) throws Exception {
        List list = new ArrayList();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("genericFieldList",list);
        map.put("resultMapType","6666666666666");

        list.add("123");
        list.add("123");
        list.add("123");

        Version version = new Version("2.3.0");
        Configuration config = new Configuration(version);
        Template template = config.getTemplate(templateFile);
        targetFile = MessageFormat.format(targetFile, info.getBigClassName(), info.getBigClassName());
        File file = new File(targetFile);
        File parentFile = file.getParentFile();
        // 创建文件目录
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 生成xml文件
        template.process(map, new FileWriter(file));
    }


}
