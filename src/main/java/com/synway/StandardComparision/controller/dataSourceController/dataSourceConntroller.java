package com.synway.StandardComparision.controller.dataSourceController;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.synway.StandardComparision.StandardComparisionApplication;
import com.synway.StandardComparision.pojo.DataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.synway.StandardComparision.service.impl.dataSourceImpl;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "StandardComparision")
/**
 * 主要编写数据库相关的信息
 */
public class dataSourceConntroller {
    private Logger logger = (Logger) LoggerFactory.getLogger(dataSourceConntroller.class);
    @Autowired() private dataSourceImpl datasourceImpl;

    @RequestMapping(value = "/findAllDataSource")
    public Object findAllDataSource(){
        List<DataSource>  result = datasourceImpl.findAllDataBase();
        //客户端分页返回值是一个列表就行
//        long total = dd.size();
//        Map<String , Object> resultAll = new HashMap<>();
//        JSONObject resultAll = new JSONObject();
//        resultAll.put("rows",dd);
//        resultAll.put("total",total);
        return result;
    }

    //新增数据库连接信息的controol层
    @RequestMapping(value = "/addDataSource")
    public Map<String, Object> addDataSource(HttpServletRequest request){
        Map<String , Object> result = new HashMap<>();
        //获取传入的数据
        Map newOneDataMap = request.getParameterMap();
        Boolean addFlag = datasourceImpl.addDataResource(newOneDataMap);
        if( addFlag){
            logger.info("新增数据库连接成功");
            result.put("state", "success");
//            logger.info("添加数据源成功");
        }else{
            logger.info("新增数据库连接失败");
            result.put("state", "error");
//            logger.info("添加数据源失败");
        }
        return result;
    }

    //修改数据库连接信息的 接口
    @RequestMapping(value = "/editDataSource")
    public Map<String, Object> editDataSource(HttpServletRequest request){
        Map<String , Object> result = new HashMap<>();
        //获取传入的数据
        Map editOneDataMap = request.getParameterMap();
        Boolean editFlag = datasourceImpl.editDataBase(editOneDataMap);
        if( editFlag){
            logger.info("修改数据库连接成功");
            result.put("state", "success");
//            logger.info("添加数据源成功");
        }else{
            logger.info("修改数据库连接失败");
            result.put("state", "error");
//            logger.info("添加数据源失败");
        }
        return result;
    }

    // 删除数据库连接信息的接口
    @RequestMapping(value = "/deleteDataSource")
    public  Map<String, Object> deleteDataSource(HttpServletRequest request){
        String[] delList=request.getParameterValues("delDataId");
        Map<String , Object> result = new HashMap<>();
        Boolean delFlag = true;
        for(int i = 0; i < delList.length; i++) {
            String dataId = delList[i];
            logger.info("删除数据库ID为："+dataId);
            delFlag = datasourceImpl.deleteDataBase(dataId);
        }
        if(delFlag){
            result.put("state", "success");
//            logger.info("添加数据源成功");
        }else{
            result.put("state", "error");
//            logger.info("添加数据源失败");
        }
        return result;
    }

    //测试数据库连接信息是否能连通
    @RequestMapping("/testConnAddEdit")
    public  Map<String, String>  testConnAddEdit(HttpServletRequest request){
        Map testDataMap = request.getParameterMap();
        Boolean testFlag = datasourceImpl.testDataBase(testDataMap);
        Map<String , String> result = new HashMap<>();
        if(testFlag){
            result.put("state", "success");
//            logger.info("添加数据源成功");
        }else{
            result.put("state", "error");
//            logger.info("添加数据源失败");
        }
        return result;
    }
}
