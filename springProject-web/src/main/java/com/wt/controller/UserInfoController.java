package com.wt.controller;

import com.wt.model.User;
import com.wt.serviceImpl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        String uuid=getUUid();
        Boolean f=userService.add(uuid,username,password);
        List<User> users=new ArrayList<>();
        if(f){
            users=userService.selectAllUsers();
        }
        return new ModelAndView("userList").addObject("userList",users);
    }

    public String getUUid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(value = "id" ,defaultValue = "0")String id, Model model){
        User user=userService.selectOneUser(id);
        model.addAttribute("user",user);
        return "userUpdate";
    }

    @RequestMapping("/update")
    public ModelAndView update(User user) throws Exception {
        boolean f=userService.update(user);
        if(!f) throw new Exception("更新失败");
        List<User> users=userService.selectAllUsers();
        return new ModelAndView("userList","userList",users);
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam("id") String userid) throws Exception {
        boolean f=userService.delete(userid);
        if(!f) throw new Exception("删除失败");
        List<User> users=userService.selectAllUsers();
        return new ModelAndView("userList","userList",users);
    }

    @RequestMapping(value="/upload",method={RequestMethod.POST})
    public ModelAndView updateItems(@RequestParam("file") MultipartFile picture,@RequestParam("fileId") String fileId) throws Exception {
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
        List<User> users=userService.selectAllUsers();
        return new ModelAndView("userList","userList",users);
    }


}
