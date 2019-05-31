package com.baizhang.bmeko.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baizhang.bmeko.bean.BaseSaleAttr;
import com.baizhang.bmeko.service.BaseSaleAttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BaseSaleAttrListController {

    @Reference
    BaseSaleAttrService baseSaleAttrService;

    @RequestMapping("/baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> baseSaleAttrList(){
        return baseSaleAttrService.baseSaleAttrList();
    }
}
