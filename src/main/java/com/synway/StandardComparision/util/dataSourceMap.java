package com.synway.StandardComparision.util;

import com.synway.StandardComparision.pojo.DataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class dataSourceMap {

    /**
     * 在xml文件中新增数据库时，如果新增的数据库类型是
     *  标准比对  则把别的名称是标准比对的改成 null
     *  @dataId 本次新增数据的数据源ID
     */
    public static void updateDataName(String dataId){
        //获取xml里面的信息
        Document doc = getXml();
        if(doc!=null){
            //获取根元素
            Element root = doc.getRootElement();
            //获取根元素中所有的 dataSource元素
            List<Element> dataSourceEleList = root.elements("dataSource");
            for(Element dataEle:dataSourceEleList){
                String dataNameEle = dataEle.elementText("DataName");
                String dataIdEle = dataEle.attributeValue("DataId");
                if(dataNameEle.equals("标准比对") && !dataIdEle.equals(dataId)){
                    dataEle.element("DataName").setText("null");
                }
            }
        }
        //将修改后的xml信息写入到xml文件中
        writerXml(doc);
    }


    /**
     * g根据固定的文件名来获取xml文件中的所有信息
     * @return Document 信息
     */
    public static Document getXml(){
        SAXReader reader = new SAXReader();
        Document doc = null;
        try{
            //读取项目下allDataBase.xml文件里面的内容
            Resource resource = new ClassPathResource("static/allXml/allDataBase.xml");
            File xmlFile = resource.getFile();
            //读取该文件
            doc = reader.read(xmlFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 将更改后的xml信息写入到指定的xml文件中
     * @param doc
     */
    public static void writerXml(Document doc){
        try {
            OutputFormat format = new OutputFormat("\t",true);
            format.setTrimText(true);
            Resource resource = new ClassPathResource("static/allXml/allDataBase.xml");
            File xmlFile = resource.getFile();
            XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFile),format);
            writer.write(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
