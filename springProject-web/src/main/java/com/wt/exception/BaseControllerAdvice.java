package com.wt.exception;


import com.wt.common.SpringCatch;
import com.wt.model.ReturnJson;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class BaseControllerAdvice {
    private static Logger log = Logger.getLogger(BaseControllerAdvice.class);

    @ExceptionHandler(value = Exception.class)   //处理并相应请求,进行返回
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //ResponseStatus修饰目标方法，无论它执行方法过程中有没有异常产生，
    // 用户都会得到异常的界面。并且为异常提供一个响应码
    @ResponseBody
    public ReturnJson exception(Exception e, WebRequest request, HttpServletResponse response) throws Exception {
        log.error(e.getMessage(),e);
//        response.sendRedirect("jsp/errorPage/500.jsp");
//        return "redirect:errorPage/500.jsp";
        if(AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
            throw e;
        }
        ReturnJson returnJson = SpringCatch.getApplicationContext().getBean("returnJson", ReturnJson.class);
        returnJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        returnJson.setData(null);
        returnJson.setMessage("请求访问失败");
        returnJson.setSuccess(false);
        return returnJson;
    }

}
