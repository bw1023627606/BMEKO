package com.baizhang.bmeko.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页跳转controller
 */
@Controller
public class IndexController {


    @RequestMapping("/index")
    public String index(){
        return "index";
    }


}
