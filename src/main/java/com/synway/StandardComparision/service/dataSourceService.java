package com.synway.StandardComparision.service;


import com.synway.StandardComparision.pojo.DataSource;

import java.util.List;
import java.util.Map;


public interface dataSourceService {
    // 在xml文件中新增数据源连接信息
    Boolean addDataResource(Map newDataMap);

    //获取xml文件中标志为 标准比对的数据库连接信息  给俞奇恩的接口
    DataSource findDataBase();

    //删除xml文件中指定的 dataId的
    Boolean deleteDataBase(String dataId);

    //编辑xml文件中的数据库连接信息
    Boolean editDataBase(Map editDataMap);

    //获取所有的数据库连接信息 用于页面展示
    List<DataSource> findAllDataBase();

    // 测试指定的数据库信息是否能连通
    Boolean testDataBase(Map testDataMap);
}
