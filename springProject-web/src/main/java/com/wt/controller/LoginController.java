package com.wt.controller;

import com.wt.annotation.NeedLogin;
import com.wt.core.Constants;
import com.wt.model.LoginInfo;
import com.wt.model.TUser;
import com.wt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value ={"/","/login"})
public class LoginController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping(value = {"/index", "/loginPage", "/"})
    @NeedLogin(value = false)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/loginCheck", method = {RequestMethod.POST, RequestMethod.GET})
    @NeedLogin(value = false)
    public ModelAndView loginCheck(LoginInfo loginInfo, HttpServletRequest request, HttpSession session) {
        TUser tUser= userService.selectOneUser(loginInfo.getUsername(), loginInfo.getPassword());
        if (tUser==null) return new ModelAndView("login", "error", "用户名密码错误");
        session.setAttribute(Constants.user,tUser);
        List<TUser> users = userService.selectAllUsers();
        return new ModelAndView("userList", "userList", users);
    }
}
