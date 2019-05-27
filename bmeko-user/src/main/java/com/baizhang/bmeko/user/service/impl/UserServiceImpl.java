package com.baizhang.bmeko.user.service.impl;


import com.baizhang.bmeko.bean.UserInfo;
import com.baizhang.bmeko.service.UserService;
import com.baizhang.bmeko.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserInfoMapper userInfoMapper;


    @Override
    public List<UserInfo> userInfoList() {

        return userInfoMapper.selectAll();
    }
}
