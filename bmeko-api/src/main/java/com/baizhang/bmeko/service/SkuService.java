package com.baizhang.bmeko.service;

import com.baizhang.bmeko.bean.SkuInfo;

import java.util.List;

public interface SkuService {
    List<SkuInfo> getSkuListBySpu(String spuId);

    void saveSku(SkuInfo skuInfo);

    SkuInfo getSkuById(String skuId);

    List<SkuInfo> getSkuListBycaralog3Id(String s);
}
