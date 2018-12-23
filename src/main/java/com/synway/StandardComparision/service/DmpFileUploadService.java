package com.synway.StandardComparision.service;

import java.io.File;
import java.util.Map;

// 将dmp文件导入到oracle库中的接口
public interface DmpFileUploadService {
    Boolean dmpUpload(File dmpFile , Map result);
}
