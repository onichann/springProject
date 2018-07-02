package com.wt.interceptor;

import com.alibaba.fastjson.JSON;
import com.wt.annotation.NeedLogin;
import com.wt.core.Constants;
import com.wt.model.TUser;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLDecoder;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger=Logger.getLogger(TokenInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            //session获取用户
            TUser user = (TUser) request.getSession().getAttribute(Constants.user);
            NeedLogin needLogin=  ((HandlerMethod) handler).getMethodAnnotation(NeedLogin.class);
            if(needLogin==null){
                needLogin=((HandlerMethod) handler).getBeanType().getAnnotation(NeedLogin.class);
            }
            if(needLogin==null){
                needLogin=((HandlerMethod) handler).getMethodAnnotation(NeedLogin.class);
            }
            //登录方法、退出登录等不需要验证
            if(needLogin!=null&&!needLogin.value()){
                logger.info(((HandlerMethod) handler).getMethod().getName()+"不需要验证,info");
                logger.debug(((HandlerMethod) handler).getMethod().getName()+"不需要验证,debug");
                logger.warn(((HandlerMethod) handler).getMethod().getName()+"不需要验证,debug");
                logger.error(((HandlerMethod) handler).getMethod().getName()+"不需要验证,error");
                return true;
            }else{
                //需要验证登录
                if(user==null){
                    Cookie[] cookies=request.getCookies();
//                    boolean userCookie=false;
                    if(ArrayUtils.isNotEmpty(cookies)){
                        for (Cookie cookie : cookies) {
                            if(Constants.userCookie.equals(cookie.getName())){
//                                userCookie=true;
                                String userStr=cookie.getValue();
                                user = JSON.parseObject(URLDecoder.decode(userStr,"UTF-8"),TUser.class);
                                request.getSession().setAttribute(Constants.user,user);
                                break;
                            }
                        }
                    }
                    if(user!=null) return true;
                    PrintWriter writer = response.getWriter();
                    response.setContentType("text/html; charset=UTF-8");
                    //writer.write("<font color='red'>用户未登录!</font>");
                    writer.write("<div style='display:none'>system::_now_need_relogin</div>");
                    writer.write("<script type='text/javascript'>var win = this;while (win._topMain !== true && win != window.top) {win = win.parent;}win.location='"+request.getContextPath()+"/jsp/errorPage/405.jsp';</script>");
                    return false;
                }

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
