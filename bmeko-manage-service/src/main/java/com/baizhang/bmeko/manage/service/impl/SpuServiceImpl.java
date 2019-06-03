package com.baizhang.bmeko.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baizhang.bmeko.bean.*;
import com.baizhang.bmeko.manage.mapper.*;
import com.baizhang.bmeko.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    SpuImageMapper spuImageMapper;
    @Override
    public List<SpuInfo> spuList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public void saveSpu(SpuInfo spuInfo) {
        //保存spuInfo的信息，返回spu的主键
        spuInfoMapper.insertSelective(spuInfo);
        String spuId = spuInfo.getId();
        System.out.println("保存信息");

        //保存spuSaleAttr销售属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
             spuSaleAttr.setSpuId(spuId);
             spuSaleAttrMapper.insert(spuSaleAttr);
             List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();

            //保存spuSaleAttrVale销售属性值
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
               spuSaleAttrValue.setSaleAttrId(spuId);
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }
       }
        //保存图片
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        System.out.println("保存图片");
        int rondom = (int)(Math.random()*20);
        if(rondom>=0&&rondom<=3){
            spuImage.setImgName("美女1号");
            spuImage.setImgUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1709561480,2949765632&fm=27&gp=0.jpg");
        }else if(rondom>=4&&rondom<=7){
            spuImage.setImgName("美女2号");
            spuImage.setImgUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=891209561,3636218284&fm=27&gp=0.jpg");
        }else if(rondom>=8&&rondom<=11){
            spuImage.setImgName("美女3号");
            spuImage.setImgUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=602650883,2101706538&fm=27&gp=0.jpg");
        }else if(rondom>=12&&rondom<=15){
            spuImage.setImgName("美女4号");
            spuImage.setImgUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1297505592,1789076279&fm=27&gp=0.jpg");
        }else{
            spuImage.setImgName("美女5号");
            spuImage.setImgUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1521901788,831589994&fm=26&gp=0.jpg");
        }
        spuImageMapper.insert(spuImage);
    }

    @Override
    public List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId) {

        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuId);
        List<SpuSaleAttr> spuSaleAttrs = spuSaleAttrMapper.select(spuSaleAttr);
        List<SpuSaleAttr> spuSaleAttrs1 = new ArrayList<>();
        for (SpuSaleAttr saleAttr:spuSaleAttrs) {
            String saleAttrId = saleAttr.getSaleAttrId();
            SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
            spuSaleAttrValue.setSaleAttrId(saleAttrId);
            spuSaleAttrValue.setSpuId(spuId);
            List<SpuSaleAttrValue> spuSaleAttrValues = spuSaleAttrValueMapper.select(spuSaleAttrValue);
            saleAttr.setSpuSaleAttrValueList(spuSaleAttrValues);
        }

        return spuSaleAttrs;
    }

    @Override
    public List<SpuImage> getSpuImageListBySpuId(String spuId) {
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap) {
        return spuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(stringStringHashMap);
    }

    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {
        return spuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
    }


}





