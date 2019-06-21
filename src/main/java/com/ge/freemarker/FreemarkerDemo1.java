package com.ge.freemarker;

import com.ge.entity.TemplateBaseMapperBo;
import com.ge.entity.User;
import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        Map map = new HashMap();
        List list = new ArrayList();
        list.add("123");
        map.put("updateList",list);
        map.put("resultMapType","6666666666666");

        TemplateBaseMapperBo bo = new TemplateBaseMapperBo();
        bo.setUpdateList(list);
        bo.setResultMapType("xxxxxxxxxxxx");

        String targetFile = MessageFormat.format(newPath, "src", "New");
        File file = new File(targetFile);
        template.process(bo, new FileWriter(file));

    }
}
