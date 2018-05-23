package com.wt.controller;

import com.wt.model.TestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping(value="/test")
public class TestController {

    @RequestMapping(value = "/checkParamters",method = RequestMethod.POST)
    @ResponseBody
    public Object test(@RequestBody TestModel testModel) throws UnsupportedEncodingException {
//        System.out.println(testModel.getIds());
       // System.out.println("map.toString() = " + map.toString());
        return "success";
    }
}
