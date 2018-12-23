package com.synway.StandardComparision.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
//import org.apache.logging.log4j.Logger;
import com.synway.StandardComparision.pojo.DataSource;
import com.synway.StandardComparision.util.dataSourceMap;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.synway.StandardComparision.service.dataSourceService;
import org.springframework.stereotype.Service;

@Service
public class dataSourceImpl implements dataSourceService {
    private Logger logger = LoggerFactory.getLogger(dataSourceImpl.class);

    /**
     * 将前端传入的map数据写入到xml文件中 以下是要传入参数的例子
     * {"DataName"='标准比对', "DataConnection"='是', "UserName"='ceshi',"PassWord"='ceshi', "JdbcUrl"='1823923:2320/df'}
     * @param newDataMap
     * @return  是否成功插入到xml文件
     */
    @Override
    public  Boolean addDataResource(Map newDataMap){
//        Map<String ,String> newDataBaseMap = new HashMap<>();
//        newDataBaseMap.put("DataName","标准比对");
//        newDataBaseMap.put("UserName","ceshi");
//        newDataBaseMap.put("PassWord","ceshi");
//        newDataBaseMap.put("JdbcUrl","1823923:2320/df");
//        newDataBaseMap.put("DataConnection","是");
        Boolean flag = true;
        try {
            //给新增的数据源设置uuid
            String dataId = UUID.randomUUID().toString().replace("-","");
            // 获取传入数据库连接信息中的 数据库类型(标准比对/null)
            String dataName = ((String[]) newDataMap.get("dataName"))[0];
            String userName =  ((String[]) newDataMap.get("userName"))[0];
            String passWord = ((String[]) newDataMap.get("passWord"))[0];
            String jdbcUrl =  ((String[]) newDataMap.get("jdbcUrl"))[0];
            String fromUser = ((String[]) newDataMap.get("fromUser"))[0];
            String toUser = ((String[]) newDataMap.get("toUser"))[0];
//            String dataConnection =  ((String[]) newDataMap.get("DataConnection"))[0];
            if(dataName.equals("标准比对")){
                //如果新增的数据库类型是  标准比对，则把其它的是标准比对的改成 null
                dataSourceMap.updateDataName(dataId);
            }
            //获取xml里面的信息
            Document doc = dataSourceMap.getXml();
            if(doc!=null){
                //获取根元素
                Element root = doc.getRootElement();
                Element stuEle = root.addElement("dataSource");
                stuEle.addAttribute("DataId",dataId);
                stuEle.addElement("DataName").setText(dataName);
                stuEle.addElement("UserName").setText(userName);
                stuEle.addElement("PassWord").setText(passWord);
                stuEle.addElement("JdbcUrl").setText(jdbcUrl);
                stuEle.addElement("FromUser").setText(fromUser);
                stuEle.addElement("ToUser").setText(toUser);
//                stuEle.addElement("DataConnection").setText(dataConnection);
                dataSourceMap.writerXml(doc);
            }else{
                //TODO 需要加的东东
                System.out.println("没有这个xml文件");
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取数据库的连接信息  只获取标志为  标准比对的数据库
     * @return  DataSource实体类
     */
    @Override
    public DataSource findDataBase() {
        //获取xml里面的信息
        Document doc = dataSourceMap.getXml();
        DataSource dataSource = new DataSource();
        try {
            if(doc!=null){
                //获取根元素
                Element root = doc.getRootElement();
                //获取根元素中所有的 dataSource元素
                List<Element> dataSourceEleList = root.elements("dataSource");
                for(Element dataEle:dataSourceEleList){
                    String dataNameEle = dataEle.elementText("DataName");
                    //                System.out.println(dataNameEle);
                    if(dataNameEle.equals("标准比对")){
                        dataSource.setDataName(dataNameEle);
                        dataSource.setDataId(dataEle.attributeValue("DataId"));
                        dataSource.setJdbcUrl(dataEle.elementText("JdbcUrl"));
                        dataSource.setUserName(dataEle.elementText("UserName"));
                        dataSource.setPassWord(dataEle.elementText("PassWord"));
                        dataSource.setFromUser(dataEle.elementText("FromUser"));
                        dataSource.setToUser(dataEle.elementText("ToUser"));
//                        dataSource.setDataConnection(dataEle.elementText("DataConnection"));
                    }
                }
            }else{
                System.out.println("没有这个xml文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /**
     * 根据传入的的dataId，在xml文件中删除指定的数据库信息
     * @param dataId  数据库在xml中的dataId
     * @return
     */
    @Override
    public Boolean deleteDataBase(String dataId) {
        Boolean flag = false;
        try {
            //获取xml里面的信息
            Document doc = dataSourceMap.getXml();
            Element root = doc.getRootElement();
            //获取根元素中所有的 dataSource元素
            List<Element> dataSourceEleList = root.elements("dataSource");
            for(Element dataEle:dataSourceEleList){
                if(dataEle.attributeValue("DataId").equals(dataId)){
                    dataEle.getParent().remove(dataEle);
                    flag=true;
                    break;
                }
            }
            dataSourceMap.writerXml(doc);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据传入的map字典修改在xml文件中的数据库连接信息
     * @param editDataMap
     * @return
     */
    @Override
    public Boolean editDataBase(Map editDataMap) {
        Boolean flag = true;
        try {
            String dataId = ((String[]) editDataMap.get("dataId"))[0];
            String dataName = ((String[]) editDataMap.get("dataName"))[0];
            String userName = ((String[]) editDataMap.get("userName"))[0];
            String passWord = ((String[]) editDataMap.get("passWord"))[0];
            String jdbcUrl = ((String[]) editDataMap.get("jdbcUrl"))[0];
            String fromUser = ((String[]) editDataMap.get("fromUser"))[0];
            String toUser = ((String[]) editDataMap.get("toUser"))[0];
            if(dataName.equals("标准比对")){
                //如果修改的数据库类型是  标准比对，则把其它的是标准比对的改成 null
                dataSourceMap.updateDataName(dataId);
            }
            //获取xml里面的信息
            Document doc = dataSourceMap.getXml();
            if(doc!=null){
                //获取根元素
                Element root = doc.getRootElement();
                List<Element> dataSourceEleList = root.elements("dataSource");
                for(Element dataEle:dataSourceEleList){
                    String dataIdEle = dataEle.attributeValue("DataId");
                    if(dataIdEle.equals(dataId)){
                        dataEle.element("DataName").setText(dataName);
                        dataEle.element("UserName").setText(userName);
                        dataEle.element("PassWord").setText(passWord);
                        dataEle.element("JdbcUrl").setText(jdbcUrl);
                        dataEle.element("FromUser").setText(fromUser);
                        dataEle.element("ToUser").setText(toUser);
//                        dataEle.element("DataConnection").setText(dataConnection);
                    }
                }
                dataSourceMap.writerXml(doc);
            }else{
                //TODO 需要加的东东
                System.out.println("没有这个xml文件");
            }
        } catch (Exception e) {
            //TODO 日志报错需要做的修改
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 获取xml文件中所有的数据库连接信息，用于页面展示
     * @return  存储dataSource实体类的列表
     */
    @Override
    public List<DataSource> findAllDataBase() {
        //获取xml里面的信息
        Document doc = dataSourceMap.getXml();
        List<DataSource> allDataBase = new ArrayList<>();
        try {
            if(doc!=null){
                //获取根元素
                Element root = doc.getRootElement();
                //获取根元素中所有的 dataSource元素
                List<Element> dataSourceEleList = root.elements("dataSource");
                for(Element dataEle:dataSourceEleList){
                    DataSource dataSource = new DataSource();
                    dataSource.setDataId(dataEle.attributeValue("DataId"));
                    dataSource.setDataName(dataEle.elementText("DataName"));
                    dataSource.setJdbcUrl(dataEle.elementText("JdbcUrl"));
                    dataSource.setUserName(dataEle.elementText("UserName"));
                    dataSource.setPassWord(dataEle.elementText("PassWord"));
                    dataSource.setFromUser(dataEle.elementText("FromUser"));
                    dataSource.setToUser(dataEle.elementText("ToUser"));
//                    dataSource.setDataConnection(dataEle.elementText("DataConnection"));
                    allDataBase.add(dataSource);
                }
            }else{
                System.out.println("没有这个xml文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allDataBase;
    }

    /**
     * 根据传入的map数据判断数据是否连通
     * @param testDataMap
     * @return
     */
    @Override
    public Boolean testDataBase(Map testDataMap) {
        Boolean flag = true;
        try {
            String dataId = ((String[]) testDataMap.get("dataId"))[0];
            String dataName = ((String[]) testDataMap.get("dataName"))[0];
            String userName = ((String[]) testDataMap.get("userName"))[0];
            String passWord = ((String[]) testDataMap.get("passWord"))[0];
            String jdbcUrl = ((String[]) testDataMap.get("jdbcUrl"))[0];
            //如果存在多个ip
            String[] jdbcUrlList = jdbcUrl.split(",");

            for (String jdbcUrlOne : jdbcUrlList) {
                Connection connection = null;
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@"+jdbcUrlOne, userName, passWord);
                } catch (Exception e) {
                    flag = false;
                    e.printStackTrace();
//                    logger.info(userName+" 在测试连通性时出错");
//                    logger.error(e.getMessage());
//                    resultMap.put(jdbcUrl, e.getMessage());
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
//                            resultMap.put(jdbcUrl, "success");
                        } catch (Exception e) {
//                            logger.error(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}

