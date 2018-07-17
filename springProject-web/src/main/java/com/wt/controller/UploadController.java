package com.wt.controller;

import com.wt.annotation.NeedLogin;
import com.wt.designPatterns.creationalPatterns.Builder.Builder;
import com.wt.exception.SpringWebException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @Value("E:\\temp\\testUpload\\")
    private String filePath;


    @PostMapping(value="/upload")
    @ResponseBody
    @NeedLogin(false)
    public Map updateItems(@RequestParam(value = "file") List<MultipartFile> files, @RequestParam(value = "map",required = false) Map params) {
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
               file.transferTo(new File(folder +File.separator+ picName + extName));
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

    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam("fileName") String fileName,
                                           @RequestHeader("User-Agent") String userAgent,
                                           Model model) throws IOException {
//        构建File
        File file=new File(filePath+File.separator+fileName);
        if(!file.exists()) throw new SpringWebException("文件不存在");
        ResponseEntity.BodyBuilder bodyBuilder=ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        fileName= URLEncoder.encode(fileName,"UTF-8");
        HttpHeaders httpHeaders=new HttpHeaders();

        if(userAgent.indexOf("MSIE")>-1){
            httpHeaders.setContentDispositionFormData("attachement",fileName);
            bodyBuilder.headers(httpHeaders);
        }else{
            bodyBuilder.headers(httpHeaders);
        }
        return bodyBuilder.body(FileUtils.readFileToByteArray(file));

    }
}
