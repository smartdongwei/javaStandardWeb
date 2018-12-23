package com.synway.StandardComparision.service.impl;

import ch.qos.logback.classic.Logger;
import com.synway.StandardComparision.pojo.DataSource;
import com.synway.StandardComparision.service.DmpFileUploadService;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class DmpFileUploadServiceImp implements DmpFileUploadService{
    private Logger logger = (Logger) LoggerFactory.getLogger(DmpFileUploadServiceImp.class);
    /**
     * 将dmp文件导入到oracle库中
     * @param dmpFile dmp文件在服务器存放的绝对路径
     * @param result  返回结果的map
     */
    @Override
    public Boolean dmpUpload(File dmpFile, Map result) {
        //获取操作系统名称  如果不等于 -1 :表示是 WINDOWS 操作系统
        int windowsFlag = System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS");
        //获取需要导入数据的数据库相关信息
        dataSourceImpl dataSourceUtil = new dataSourceImpl();
        DataSource dataBase = dataSourceUtil.findDataBase();
        StringBuffer commandBuf = new StringBuffer("imp ");
        commandBuf.append(dataBase.getUserName() + "/");
        commandBuf.append(dataBase.getPassWord() + "@");
        commandBuf.append(dataBase.getJdbcUrl());
        commandBuf.append(" fromuser=" + dataBase.getFromUser());
        commandBuf.append(" touser=" + dataBase.getToUser());
        commandBuf.append(" file=" + dmpFile);
        commandBuf.append(" ignore=n");
        Process process = null;
        if(windowsFlag!= -1){
            String[] cmds ={"cmd","/C",commandBuf.toString()};
            logger.info("导入数据库命令是:"+ StringUtils.join(cmds));
            try {
                process = Runtime.getRuntime().exec(cmds);
            } catch (Exception e) {
                logger.error("imp相关命令报错",e.getMessage());
                result.put("message",e.getMessage());
                return false;
            }
        }else{
            String[] cmdLinux ={commandBuf.toString()};
            logger.info("导入数据库命令是:"+StringUtils.join(cmdLinux));
            try {
                process = Runtime.getRuntime().exec(cmdLinux);
            } catch (Exception e) {
                logger.error("imp相关命令报错",e.getMessage());
                result.put("message",e.getMessage());
                return false;
            }
        }

        boolean shouldClose = false;
        try {
            //以下必须设置为GBK，否则会出现乱码的情况
            InputStreamReader isr = new InputStreamReader(process.getErrorStream(),"gbk");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                logger.info(line);
                if (line.indexOf("????") != -1) {
                    shouldClose = true;
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("读取数据库输出日志报错",e.getMessage());
            shouldClose = true;
        }
        if (shouldClose)
            process.destroy();
        int exitVal;
        try {
            exitVal = process.waitFor();
        } catch (InterruptedException e) {
            logger.error("获取运行情况报错",e.getMessage());
            e.printStackTrace();
            return false;
        }
        if(exitVal==0) {
            return true;
        }else{
            return false;
        }

    }
}
