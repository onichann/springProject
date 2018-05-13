package com.wt.controller;

import com.wt.model.LoginCommand;
import com.wt.model.User;
import com.wt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value ={"/","/login"})
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/index", "/loginPage", "/"})
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public ModelAndView loginCheck(LoginCommand loginCommand, HttpServletRequest request, HttpSession session) {
        boolean isValidUser = userService.hasMatchUser(loginCommand.getUsername(), loginCommand.getPassword());
        if (!isValidUser) return new ModelAndView("login", "error", "用户名密码错误");
        User user = userService.findUserByUsernName(loginCommand.getUsername());
        user.setLastIp(request.getRemoteAddr());
        user.setLastVisit(new Date());
        userService.loginSuccess(user);
        request.getSession().setAttribute("user", user);
        ModelMap modelMap = new ModelMap();
        modelMap.put("key", "localhost");
        return new ModelAndView("main").addObject("test", modelMap);
    }

    @RequestMapping(value = {"/test/{userId}/{userPassword}/test", "/test/ant?/{userId}/{userPassword}"})
    @ResponseBody
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

    @RequestMapping(value = "/loginCheck2", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView loginChecke2(LoginCommand loginCommand, HttpServletRequest request, HttpSession session) {
        boolean isValidUser = userService.hasMatchUser2(loginCommand.getUsername(), loginCommand.getPassword());
        if (!isValidUser) return new ModelAndView("login", "error", "用户名密码错误");
        List<User> users = userService.selectAllUsers();
        return new ModelAndView("userList", "userList", users);
    }
}
