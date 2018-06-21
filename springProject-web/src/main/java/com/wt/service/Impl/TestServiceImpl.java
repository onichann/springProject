package com.wt.service.Impl;

import com.wt.annotation.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

    @Log(operationType = "add",operationInfo = "增加")
    public String doTest(String name,String age){
        System.out.println("=====执行test方法=====");
        return StringUtils.trimToEmpty(name)+StringUtils.trimToEmpty(age);
    }
}
