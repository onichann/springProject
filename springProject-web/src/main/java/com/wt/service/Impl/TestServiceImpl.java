package com.wt.service.Impl;

import com.wt.annotation.Log;
import com.wt.service.TestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

    @Override
    @Log(operationType = "add",operationInfo = "增加")
    public String doTest(String name,String age){
        System.out.println("=====执行test方法=====");
        if(name.length() < 6 || name.length() > 10)
        {
            throw new IllegalArgumentException("name参数的长度必须大于5，小于11！");
        }
        return StringUtils.trimToEmpty(name)+StringUtils.trimToEmpty(age);
    }
}
