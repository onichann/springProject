package com.wt.interceptor;

import com.alibaba.fastjson.JSON;
import com.wt.annotation.NeedLogin;
import com.wt.model.User;
import com.wt.core.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            //session获取用户
            User user = (User) request.getSession().getAttribute(Constants.user);
            NeedLogin needLogin=  ((HandlerMethod) handler).getMethodAnnotation(NeedLogin.class);
            if(needLogin==null){
                needLogin=((HandlerMethod) handler).getBeanType().getAnnotation(NeedLogin.class);
            }
            if(needLogin==null){
                needLogin=((HandlerMethod) handler).getMethodAnnotation(NeedLogin.class);
            }
            //登录方法、退出登录等不需要验证
            if(needLogin!=null&&!needLogin.value()){
                return true;
            }else{
                //需要验证登录
                if(user==null){
                    Cookie[] cookies=request.getCookies();
                    if(ArrayUtils.isNotEmpty(cookies)){
                        for (Cookie cookie : cookies) {
                            if(Constants.userCookie.equals(cookie.getName())){
                                String userStr=cookie.getValue();
                                User user1 = JSON.parseObject(userStr,User.class);
                                request.getSession().setAttribute(Constants.user,user1);
                                break;
                            }
                        }
                    }

                }
                return false;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request,response,handler,modelAndView);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request,response,handler,ex);
    }

}
