package com.wt.listener;

import com.wt.service.Impl.UserServiceImpl;
import com.wt.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitDataListener implements ServletContextListener {
    private static final Logger logger= Logger.getLogger(InitDataListener.class);

    @Autowired
    private UserService userService;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContext context =WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        UserServiceImpl userServiceBean= context.getBean("userService", UserServiceImpl.class);
        logger.info("监听到信息:"+userServiceBean);
        context.getAutowireCapableBeanFactory().autowireBean(this);
        logger.info("监听到信息:"+userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
