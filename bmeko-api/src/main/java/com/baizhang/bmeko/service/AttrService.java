package com.baizhang.bmeko.service;

import com.baizhang.bmeko.bean.BaseAttrInfo;

import java.util.List;

public interface AttrService {
    List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttr(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrInfo(String id);
}
