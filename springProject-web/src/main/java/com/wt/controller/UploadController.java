package com.wt.controller;

import com.wt.annotation.NeedLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/uploadController")
public class UploadController {

    @Value("H:\\temp\\testUpload\\")
    private String filePath;


    @RequestMapping(value="/upload",method={RequestMethod.POST})
    @ResponseBody
    @NeedLogin(false)
    public Map updateItems(@RequestParam(value = "file",required = false) List<MultipartFile> files, @RequestParam(value = "map",required = false) Map params) {
        Map map=new HashMap<>();
        try {
           long  startTime=System.currentTimeMillis();
           for(MultipartFile file:files){
               String picName = UUID.randomUUID().toString();
               // 截取文件的扩展名(如.jpg)
               String oriName = file.getOriginalFilename();
               String extName = oriName.substring(oriName.lastIndexOf("."));
               // 保存文件
               File folder=new File(filePath);
               if(!folder.exists()) folder.mkdirs();
               file.transferTo(new File(folder +"\\"+ picName + extName));
           }
            long  endTime=System.currentTimeMillis();
            System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
            map.put("success",true);
            map.put("info","上传成功:传入的文件共"+files.size()+"个,耗时:"+String.valueOf(endTime-startTime)+"ms");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("info","上传失败.");
        }
        return map;
    }
}
