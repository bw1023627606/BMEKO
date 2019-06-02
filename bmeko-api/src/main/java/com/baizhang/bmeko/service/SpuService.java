package com.baizhang.bmeko.service;

import com.baizhang.bmeko.bean.SpuImage;
import com.baizhang.bmeko.bean.SpuInfo;
import com.baizhang.bmeko.bean.SpuSaleAttr;

import java.util.List;

public interface SpuService {
    List<SpuInfo> spuList(String catalog3Id);

    void saveSpu(SpuInfo spuInfo);

    List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId);

    List<SpuImage> getSpuImageListBySpuId(String spuId);
}
