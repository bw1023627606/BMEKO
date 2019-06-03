package com.baizhang.bmeko.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baizhang.bmeko.bean.SkuInfo;

import com.baizhang.bmeko.bean.SkuSaleAttrValue;
import com.baizhang.bmeko.bean.SpuSaleAttr;
import com.baizhang.bmeko.service.SkuService;
import com.baizhang.bmeko.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {


    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;


    @RequestMapping("index")
    public String index(){
        return "demo";
    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map){
        SkuInfo skuInfo = skuService.getSkuById(skuId);


        map.put("skuInfo",skuInfo);
        String spuId = skuInfo.getSpuId();

//        //当前sku所包含的新销售属性
//        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
//
//        //spu销售属性列表
//        List<SpuSaleAttr> saleAttrListBySpuId = spuService.getSaleAttrListBySpuId(spuId);

        //spu的sku和销售属性对应关系的hash表
        List<SkuInfo> spuInfos = spuService.getSkuSaleAttrValueListBySpu(spuId);

        HashMap<String,String> stringStringHashMap1 = new HashMap<>();
        for (SkuInfo spuInfo : spuInfos) {
            String v = spuInfo.getId();

            List<SkuSaleAttrValue> skuSaleAttrValueList = spuInfo.getSkuSaleAttrValueList();
            String k = "";
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                k=k+"|"+skuSaleAttrValue.getSaleAttrValueId();
            }
            stringStringHashMap1.put(k,v);
        }

        String skuJson = JSON.toJSONString(stringStringHashMap1);
        map.put("skuJson",skuJson);
        //销售属性列表
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("spuId",spuId);
        stringStringHashMap.put("skuId",skuId);
        List<SpuSaleAttr> saleAttrListBySpuId = spuService.getSpuSaleAttrListCheckBySku(stringStringHashMap);
        map.put("spuSaleAttrListCheckBySku",saleAttrListBySpuId);
        return "item";
    }
}
