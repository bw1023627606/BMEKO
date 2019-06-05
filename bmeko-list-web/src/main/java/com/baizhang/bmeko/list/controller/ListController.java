package com.baizhang.bmeko.list.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baizhang.bmeko.bean.BaseAttrInfo;
import com.baizhang.bmeko.bean.SkuLsAttrValue;
import com.baizhang.bmeko.bean.SkuLsInfo;
import com.baizhang.bmeko.bean.SkuLsParam;
import com.baizhang.bmeko.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ListController {


    @Reference
    ListService listService;


    @RequestMapping("/list.html")
    public String search(SkuLsParam skuLsParam, ModelMap map) {
        List<SkuLsInfo> skuLsInfo =  listService.search(skuLsParam);

        //封装平台属性列表
        List<BaseAttrInfo> baseAttrInfos = getAttrValueIds(skuLsInfo);


        map.put("skuLsInfoList",skuLsInfo);
        return "list";
    }

    private List<BaseAttrInfo> getAttrValueIds(List<SkuLsInfo> skuLsInfo) {

        Set<String> valueIds = new HashSet<>();

        for (SkuLsInfo lsInfo : skuLsInfo) {
            List<SkuLsAttrValue> skuAttrValueList = lsInfo.getSkuAttrValueList();

            for (SkuLsAttrValue skuLsAttrValue : skuAttrValueList) {
                String valueId = skuLsAttrValue.getValueId();
                valueIds.add(valueId);
            }
        }

        //根据去重复后的id集合检索平台关联到的平台属性列表
        //05 12分49

        return null;
    }




    @RequestMapping("/index")
    public String index() {
        return "index";
    }


}
