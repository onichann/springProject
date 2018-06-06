package com.wt.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringCatch implements ApplicationContextAware {

    private static  ApplicationContext ctx;
    private static SpringCatch springCatch=new SpringCatch();
    private SpringCatch(){};
    public static SpringCatch getInstance(){
        return springCatch;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx=applicationContext;
    }

    public ApplicationContext applicationContext(){
        return ctx;
    }


}
