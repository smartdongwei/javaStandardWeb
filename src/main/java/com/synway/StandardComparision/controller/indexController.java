package com.synway.StandardComparision.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "StandardComparision")
/**
 * 主页面的跳转功能，以及相关功能页面的跳转
 */
public class indexController {
    @RequestMapping(value = "/")
    public String index(){
        return "/index.html";
    }

    @RequestMapping(value = "/dataSource.html")
    public String dataSource(){
        return "/dataSource.html";
    }

    // dmp文件上传的页面
    @RequestMapping(value = "/dmpFileUpload.html")
    public String dmpFileUpload(){
        return "/dmpFileUpload.html";
    }

    // 日志实时生成的页面
    @RequestMapping(value = "/stompDemo.html")
    public String stompDemo(){
        return "/stompDemo.html";
    }
}
