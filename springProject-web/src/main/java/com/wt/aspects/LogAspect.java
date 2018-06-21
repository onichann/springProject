package com.wt.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


/**
 * 日志切面
 * https://www.cnblogs.com/jianjianyang/p/4910851.html
 * https://blog.csdn.net/it_zouxiang/article/details/52576917
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger= Logger.getLogger(LogAspect.class);

    /**
     * 定义切点拦截Log注解
     */
    @Pointcut("within(com.wt..*)&&@annotation(com.wt.annotation.Log)")
    public void logPonintCut(){}

    @Before("logPonintCut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("===========before============");
    }

    @Around("logPonintCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint){
        Object result=null;
        try {
            System.out.println("=============Around目标方法执行前================");
            result=proceedingJoinPoint.proceed();
            System.out.println("=============Around目标方法执行test后================");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("==========Around目标方法执行异常=====================");
        }
        System.out.println("=====Around目标方法执后=========");
        return result+"11";
    }

    @After("logPonintCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("=========after=======");
        try {
//            获取注解信息,保存日志信息

        }catch (Exception e){

        }
    }

    @AfterReturning(pointcut = "logPonintCut()",returning = "rvt")
    public void doAfterReturning(JoinPoint joinPoint,Object rvt){
        System.out.println("==========afterReturning==========,返回值："+rvt);
    }

    @AfterThrowing(pointcut = "logPonintCut()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
        System.out.println("==========afterThrowing==========");
    }

}
