package com.baizhang.bmeko.list.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListController {


    @RequestMapping("/list")
    public String search() {
        return "list";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }


}
