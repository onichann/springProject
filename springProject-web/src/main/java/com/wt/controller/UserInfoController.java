package com.wt.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wt.annotation.NeedLogin;
import com.wt.common.SpringCatch;
import com.wt.core.IDUtils;
import com.wt.model.TUser;
import com.wt.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserService userService;

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "userAdd";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ModelAndView add(@RequestParam(value = "userName") String username,
                      @RequestParam(value = "userPwd") String password){
        TUser tUser= SpringCatch.getApplicationContext().getBean("tUser",TUser.class);
        TUser tUser2= SpringCatch.getApplicationContext().getBean("tUser",TUser.class);
        Logger.getLogger(UserInfoController.class).info("=="+(tUser==tUser2));
        String uuid= IDUtils.getUUID();
        tUser.setFeatid(uuid);
        tUser.setUsername(username);
        tUser.setPassword(password);
        Boolean f=userService.addUser(tUser);
        List<TUser> users=new ArrayList<>();
        if(f){
            users=userService.selectAllUsers();
        }
        return new ModelAndView("userList").addObject("userList",users);
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(value = "id" ,defaultValue = "0")String id, Model model){
        TUser user=userService.selectUserById(id);
        model.addAttribute("user",user);
        return "userUpdate";
    }

    @RequestMapping("/update")
    public ModelAndView update(TUser user) throws Exception {
        boolean f=userService.updateUser(user);
        if(!f) throw new Exception("更新失败");
        List<TUser> users=userService.selectAllUsers();
        return new ModelAndView("userList","userList",users);
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam("id") String userid) throws Exception {
        boolean f=userService.deleteUser(userid);
        if(!f) throw new Exception("删除失败");
        List<TUser> users=userService.selectAllUsers();
        return new ModelAndView("userList","userList",users);
    }

    /**
     * 测试分页插件
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    @NeedLogin(false)
    public Object testSelect(@RequestParam(value = "pageNo",defaultValue = "1")int pageNo,@RequestParam("pageSize") int pageSize){
        Page<TUser> page=PageHelper.startPage(pageNo,pageSize);
        List<TUser> list=userService.selectAllUsers();
        PageInfo<TUser> info = new PageInfo<TUser>(page);
        System.out.println(info.toString());
        return list;
    }

}
