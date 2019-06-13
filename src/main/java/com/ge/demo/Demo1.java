package com.ge.demo;

import com.ge.utils.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;
import org.dom4j.tree.DefaultAttribute;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dengzhipeng
 * @date 2019/06/13
 */
public class Demo1 {
//    String xmlPath = "src/Demo.xml";
    String xmlPath = "src/Template.xml";

    public Document getDocument() throws Exception {
        SAXReader sax = new SAXReader() ;
        //获取一个document对象
        Document doc = sax.read(new FileInputStream(xmlPath));
        return doc;
    }

    public void wireteXml(Document document) throws Exception {
        // 格式化输出流，同时指定编码格式。也可以在FileOutputStream中指定。
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream("src/new.xml"),format);
        writer.write(document);
        writer.close();
    }

    /**
     * 获取根节点，获取节点所有属性
     * @throws Exception
     */
    @Test
    public void t1() throws Exception {
        Document document = getDocument();
        //获取XML文档的根元素
        Element root = document.getRootElement();
        System.out.println(root.getName());
        // 获取该节点属性
        List rootList = root.attributes();
        for (Object o : rootList) {
            DefaultAttribute attribute = (DefaultAttribute) o;
            System.out.print("节点属性名:" + attribute.getValue()+ " ----- 节点属性值:" + attribute.getValue());
            System.out.println();
        }
    }

    /**
     * 给节点添加属性，写回xml文件
     * @throws Exception
     */
    @Test
    public void t2() throws Exception {
        Document document = getDocument();
        //获取XML文档的根元素
        Element root = document.getRootElement();
        // 给某个节点添加属性
        root.addAttribute("namespace", "com.dzp.shuai");
        // 新建xml文件
        wireteXml(document);
    }

    /**
     * 增加节点和节点属性值
     * @throws Exception
     */
    @Test
    public void t3() throws Exception {
        Document document = getDocument();
        //获取XML文档的根元素
        Element root = document.getRootElement();
        BaseElement node = new BaseElement("sql");

        node.addAttribute("id","Base_Column_List");

        List list = new ArrayList();
        list.addAll(Collections.singleton("id, stock_user_id, code, last_block, update_date, is_del,id, stock_user_id, code, last_block, update_date, is_del,id, stock_user_id, code, last_block, update_date, is_del"));
        node.setContent(list);


        root.add(node);
        // 新建xml文件
        wireteXml(document);
    }
}
