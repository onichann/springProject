package com.wt.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @RequestMapping(value="/upload",method={RequestMethod.POST})
    @ResponseBody
    public Map updateItems(@RequestParam("file") MultipartFile picture,@RequestParam("fileId") String fileId) {
        Map map=new HashMap<>();
        try {
            // 把图片保存到图片目录下
            // 保存图片，这个图片有的时候文件名可能会重复，你保存多了会把原来的图片给覆盖掉，这就不太合适了。
            // 所以为每个文件生成一个新的文件名
            long  startTime=System.currentTimeMillis();
            String picName = UUID.randomUUID().toString();
            // 截取文件的扩展名(如.jpg)
            String oriName = picture.getOriginalFilename();
            String extName = oriName.substring(oriName.lastIndexOf("."));
            // 保存文件
            picture.transferTo(new File("H:\\temp\\images\\" + picName + extName));
            long  endTime=System.currentTimeMillis();
            System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
            map.put("success",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }


}
