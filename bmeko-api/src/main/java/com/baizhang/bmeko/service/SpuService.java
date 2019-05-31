package com.baizhang.bmeko.service;

import com.baizhang.bmeko.bean.SpuInfo;

import java.util.List;

public interface SpuService {
    List<SpuInfo> spuList(String catalog3Id);
}
