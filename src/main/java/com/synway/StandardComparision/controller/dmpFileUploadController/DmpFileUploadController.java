package com.synway.StandardComparision.controller.dmpFileUploadController;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.synway.StandardComparision.service.DmpFileUploadService;
import com.synway.StandardComparision.service.impl.DmpFileUploadServiceImp;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "StandardComparision")
public class DmpFileUploadController {
    private Logger logger = (Logger) LoggerFactory.getLogger(DmpFileUploadController.class);
    @Autowired() private DmpFileUploadService dmpFileUploadServiceImp;

    private static final String TEMP_UPLOAD = System.getProperty("user.dir")+"/dmpTmp";

    @RequestMapping(value = "/uploadDmlFile" , method = RequestMethod.POST)
    public Object uploadDmlFile(@RequestParam("file") MultipartFile dmpFile){

        //判断存储dmp文件的临时目录是否存在，不存在则创建
        File dmpDirectory = new File(TEMP_UPLOAD);
        if(!dmpDirectory.exists()){
            dmpDirectory.mkdir();
        }
        Map<String , Object> result = new HashMap<>();
//        result.put("status",400);
        File dmpDemoFile=null;
        try {
            //获取上传的文件，将文件下载到本地的临时目录下
            String fileName = dmpFile.getOriginalFilename();
            logger.info("开始将"+fileName+"导入到ORACLE数据库中");
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            //生成临时文件
            dmpDemoFile = File.createTempFile(UUID.randomUUID().toString(),prefix ,dmpDirectory );
            dmpFile.transferTo(dmpDemoFile);
            Boolean upLoadFlag = dmpFileUploadServiceImp.dmpUpload(dmpDemoFile  , result);
            if(upLoadFlag){
                result.put("status",200);
            }else{
                result.put("status",400);
            }
            result.put("filename",fileName);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(dmpDemoFile.exists()){
                dmpDemoFile.delete();
            }
        }

        return result;
    }
}
