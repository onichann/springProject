package com.wt.controller;

import com.google.gson.Gson;
import com.wt.annotation.NeedLogin;
import com.wt.core.Constants;
import com.wt.model.LoginInfo;
import com.wt.model.TUser;
import com.wt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping(value ={"/","/login"})
public class LoginController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping(value = { "/loginPage", "/"})
    @NeedLogin(value = false)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/loginCheck", method = {RequestMethod.POST, RequestMethod.GET})
    @NeedLogin(value = false)
    public ModelAndView loginCheck(LoginInfo loginInfo, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws UnsupportedEncodingException {
        TUser tUser= userService.selectOneUser(loginInfo.getUsername(), loginInfo.getPassword());
        if (tUser==null) return new ModelAndView("login", "error", "用户名密码错误");
        session.setAttribute(Constants.user,tUser);
        Gson gson=new Gson();
        Cookie cookie=new Cookie(Constants.userCookie, URLEncoder.encode(gson.toJson(tUser),"UTF-8"));
        cookie.setMaxAge(30*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        List<TUser> users = userService.selectAllUsers();
        return new ModelAndView("userList", "userList", users);
    }

    @RequestMapping("/loginOut")
    public ModelAndView loginOut(HttpSession session,RedirectAttributes model){
        session.removeAttribute(Constants.user);
//        return new ModelAndView("redirect:/login/loginPage");
        model.addFlashAttribute("name","wutong");
        return new ModelAndView("redirect:/login/test");
    }

    @RequestMapping("/test")
    @NeedLogin(false)
    public String test(@ModelAttribute("name") String param, Model model){
        model.addAttribute("error",param);
        return "login";
    }


}
