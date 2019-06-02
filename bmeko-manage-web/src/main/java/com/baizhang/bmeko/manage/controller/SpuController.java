package com.baizhang.bmeko.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baizhang.bmeko.bean.SpuImage;
import com.baizhang.bmeko.bean.SpuInfo;
import com.baizhang.bmeko.bean.SpuSaleAttr;
import com.baizhang.bmeko.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
public class SpuController {

    @Reference
    SpuService spuService;





    @RequestMapping("/getSpuImageListBySpuId")
    @ResponseBody
    public List<SpuImage> getSpuImageListBySpuId(String spuId){
        List<SpuImage> spuImages = spuService.getSpuImageListBySpuId(spuId);

        return spuImages;
    }

    @RequestMapping("/getSaleAttrListBySpuId")
    @ResponseBody
    public List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId){
        List<SpuSaleAttr> spuSaleAttrs = spuService.getSaleAttrListBySpuId(spuId);

        return spuSaleAttrs;
    }


    @RequestMapping("/spuList")
    @ResponseBody
    public List<SpuInfo> spuList(String catalog3Id){
        List<SpuInfo> spuInfos = spuService.spuList(catalog3Id);
        return spuInfos;
    }

    @RequestMapping("/saveSpu")
    @ResponseBody
    public String saveSpu(SpuInfo spuInfo){
        spuService.saveSpu(spuInfo);
        return "success";
    }


    /*@RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file){
        //fdfs的上传工具
        return "https://ecmb.bdimg.com/tam-ogel/1a8e9380811fe098ff7288695a9cfcd4_259_194.jpg";
    }*/



}
