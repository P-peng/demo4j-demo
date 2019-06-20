package com.ge.freemarker;

import com.ge.entity.User;
import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengzhipeng
 * @date 2019/06/14
 */
public class FreemarkerDemo1 {

    String xmlPath = "src/Template.xml";
    String newPath = "src/new.xml";
    String xmlDemo = "src/Demo.xml";


    @Test
    public void t1() throws IOException, TemplateException {
        Version version = new Version("2.3.0");
        Configuration config = new Configuration(version);
        Template template = config.getTemplate(xmlPath);
        System.out.println();
        Map map = new HashMap();
        map.put("update","123");
        template.process(map, new FileWriter(newPath));

    }
}
