package com.baizhang.bmeko.service;

import com.baizhang.bmeko.bean.SkuLsInfo;
import com.baizhang.bmeko.bean.SkuLsParam;

import java.util.List;

public interface ListService {
    List<SkuLsInfo> search(SkuLsParam skuLsParam);
}
