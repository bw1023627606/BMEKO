package com.baizhang.bmeko.service;

import com.baizhang.bmeko.bean.SkuInfo;
import com.baizhang.bmeko.bean.SpuImage;
import com.baizhang.bmeko.bean.SpuInfo;
import com.baizhang.bmeko.bean.SpuSaleAttr;

import java.util.List;
import java.util.Map;

public interface SpuService {
    List<SpuInfo> spuList(String catalog3Id);

    void saveSpu(SpuInfo spuInfo);

    List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId);

    List<SpuImage> getSpuImageListBySpuId(String spuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap);

    List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);
}
