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
public class MyGenerator {
    /**
     * 统一定义截取字符串的长度为多少字母
     */
    private static final int FEILDL_ENGTH = 1;

    public static void main(String[] args) throws Exception {

        ClassInfo info = new ClassInfo(User.class);

        // 创建表
//        createTable(info);

        // 生成xml文件
        createMyBatisXML(info, "src/Mapper.xml", "src/main/java/{0}/mapper/{1}Mapper.xml");

        // mapper接口
//        createMapper(info, "Mapper.java", "src/main/java/{0}/mapper/{1}Mapper.java");

        // 生成QueryOBject
//        createQueryObject(info, "QueryObject.java", "src/main/java/{0}/query/{1}QueryObject.java");

        // 生成Service接口
//        createService(info, "IService.java", "src/main/java/{0}/service/I{1}Service.java");

        // 生成Service实现类
//        createServiceImpl(info, "ServiceImpl.java", "src/main/java/{0}/service/impl/{1}ServiceImpl.java");
    }

//    @SuppressWarnings("deprecation")
//    private static Configuration config = new Configuration();
//    static {
//        try {
//            config.setDirectoryForTemplateLoading(new File("templates"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void createMyBatisXML(ClassInfo info, String templateFile, String targetFile) throws Exception {

        LinkedHashMap<String, String> propertys = info.getFieldMap();
        LinkedHashMap<String, String> importFieldMap = info.getImportFieldMap();
        List<String> genericFieldList = info.getGenericFieldList();
        List<AssociationObject> association = info.getAssociation();
        StringBuilder insert1 = new StringBuilder().append("insert into " + info.getBigClassName() + "  (");
        StringBuilder insert2 = new StringBuilder().append("  values( ");

        StringBuilder delete = new StringBuilder().append("delete from " + info.getBigClassName() + " where id =#{id}");

        StringBuilder update = new StringBuilder().append("update " + info.getBigClassName() + " set ");

        StringBuilder selectselectByPrimaryKey = new StringBuilder().append("select ");

        ListIterator<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(propertys.entrySet())
                .listIterator(propertys.size());
        while (list.hasPrevious()) {

            String key = list.previous().getKey();

            System.out.println(key);
            String value = propertys.get(key);
            if (JudgeType.judgeType(value)) {
                // 插入时不需要主键
                if (!key.equals("id")) {
                    genericFieldList.add(key);
                    insert1.append(" " + key + " ,");
                    insert2.append(" #{" + key + "} ,");
                    update.append(" " + key + "=#{" + key + "}, ");
                }
                selectselectByPrimaryKey
                        .append(info.getSmallClassName().subSequence(0, MyGenerator.FEILDL_ENGTH) + "." + key + ", ");
            } else {
                // 外键关联的相关属性
                AssociationObject associationObject = new AssociationObject();
                associationObject.setColumnPrefix(key.substring(0, MyGenerator.FEILDL_ENGTH) + "_");
                associationObject.setProperty(key);
                associationObject.setJavaType(value);
                association.add(associationObject);
                insert1.append(" " + key + "_id ,");
                insert2.append(" #{" + key + ".id} ,");

                update.append(" " + key + "_id=#{" + key + ".id}, ");
                int index = value.lastIndexOf(".");
                value = value.substring(index + 1);

                importFieldMap.put(key, value);

            }
        }
        int index = insert1.lastIndexOf(",");
        String str1 = null;
        if (index != -1) {
            str1 = insert1.substring(0, index) + " )";
        }else {
            str1 = insert1.toString();
        }
        index = insert2.lastIndexOf(",");
        String str2 = null;
        if (index != -1) {
            str2 = insert2.substring(0, index) + " )";
        }else {
            str2 = insert2.toString();
        }
        // 增加
        info.setInsert(str1 + str2);
        System.out.println("增加操作  " + str1 + str2);

        // 删除
        info.setDelete(delete.toString());
        System.out.println("删除操作  " + delete.toString());

        index = update.lastIndexOf(",");
        String subUpdate = null;
        if (index != -1) {
            subUpdate = update.substring(0, index);
        }else {
            subUpdate = update.toString();
        }
        subUpdate = subUpdate + " where id=#{id}";
        // 更改操作
        info.setUpdate(subUpdate);
        System.out.println("更改操作  " + subUpdate);
        // 判断是否有外键
        if (importFieldMap.size() <= 0) {
            index = selectselectByPrimaryKey.lastIndexOf(",");
            String str = selectselectByPrimaryKey.substring(0, index);

            // 条数的查询
            String queryForCount = "select count(" + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH)
                    + ".id" + ") from " + info.getBigClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH);
            info.setQueryForCount(queryForCount);
            System.out.println("查询条数  " + queryForCount);

            // 查询结果集
            info.setQueryListData(str + " from " + info.getSmallClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH));
            System.out.println("查询所有  " + str + " from " + info.getSmallClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH));
            // 分页相关
            info.setLimit("limit #{currentPage},#{pageSize}");

            str = str + " from " + info.getSmallClassName() + " where "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH) + ".id=#{id}";

            // 根据主键的查询
            info.setSelectByPrimaryKey(str);
            System.out.println("主键查询  " + str);

        } else {
            Set<Map.Entry<String, String>> entrySet = importFieldMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                selectselectByPrimaryKey.append(entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + ".id as "
                        + entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + "_id ,");
            }
            index = selectselectByPrimaryKey.lastIndexOf(",");
            String str = selectselectByPrimaryKey.substring(0, index);
            str = str + " from " + info.getBigClassName() + "  "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH);
            for (Map.Entry<String, String> entry : entrySet) {
                str = str + " left join " + entry.getValue() + " "
                        + entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + " on " + "("
                        + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH) + "." + entry.getKey()
                        + "_id=" + entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + ".id)";
            }

            String queryForCount = "select count(" + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH)
                    + ".id" + ") from " + info.getBigClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH);
            info.setQueryForCount(queryForCount);
            System.out.println("查询条数  " + queryForCount);

            // 查询结果集
            info.setQueryListData(str);
            System.out.println("查询所有  " + str);
            // 分页相关
            info.setLimit("limit #{start},#{pageSize}");

            str = str + " where " + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH) + ".id=#{id}";

            // 根据主键的查询
            info.setSelectByPrimaryKey(str);
            System.out.println("主键查询  " + str);
        }

        Version version = new Version("2.3.0");
        Configuration config = new Configuration(version);
        Template template = config.getTemplate(templateFile);
        targetFile = MessageFormat.format(targetFile, info.getBasePackage().replace(".", "/"), info.getBigClassName());
        File file = new File(targetFile);
        File parentFile = file.getParentFile();
        // 创建文件目录
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 生成xml文件
        template.process(info, new FileWriter(file));
    }


}
