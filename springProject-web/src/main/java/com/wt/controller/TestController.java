package com.wt.controller;

import com.wt.annotation.NeedLogin;
import com.wt.model.TestModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/test")
public class TestController {

    @RequestMapping(value = {"/test/{userId}/{userPassword}/test", "/test/ant?/{userId}/{userPassword}"})
    @ResponseBody
    @NeedLogin(false)
    public Object test(@PathVariable("userId") int userid,
                       @PathVariable(value = "userPassword", required = false) String userPassword,
                       @RequestParam(value = "type", defaultValue = "1") int type,
                       @RequestParam(value = "key", required = false) String key) {
        String response = userid + "," + userPassword + "," + type + "," + key;
        System.out.println("response = " + response);
        Map m = new HashMap<>();
        m.put("userid", userid);
        return m;
    }

//    consumes 方法仅处理request Content-Type为“application/json”类型的请求。
    @RequestMapping(value = "/checkParamters",method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    @NeedLogin(false)
    public Object test(@RequestBody TestModel testModel) throws UnsupportedEncodingException {
//        System.out.println(testModel.getIds());
       // System.out.println("map.toString() = " + map.toString());
        return "success";
    }
}
