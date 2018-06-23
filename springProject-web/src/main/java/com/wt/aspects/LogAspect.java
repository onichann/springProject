package com.wt.aspects;

import com.wt.annotation.Log;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 日志切面
 * @Order 数值越小 ，当前切面优先执行
 * @see com.wt.annotation.Log
 */
@Aspect
@Component
@Order(0)
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

    @Around("within(com.wt..*)&&@annotation(log)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint,Log log){
        Object result=null;
        try {
            System.out.println("=============Around目标方法执行前================");

            result=proceedingJoinPoint.proceed();
            System.out.println("=============Around目标方法执行test后================");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("==========Around目标方法执行异常=====================");
            throw new RuntimeException("调用方法执行异常");
        }
        System.out.println("=====Around目标方法执后=========");
        //  方法执行成功后，可以获取注解信息,保存日志信息
        System.out.println(log.operationType());
        System.out.println(log.operationInfo());
        try {
            Object[] args=proceedingJoinPoint.getArgs();
            String methodName=proceedingJoinPoint.getSignature().getName();
            String className=proceedingJoinPoint.getTarget().getClass().getName();
            Class clazz=Class.forName(className);
            //获取方法参数类型
            Class[] parameterTypes=new Class[args.length];
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            Method method=clazz.getDeclaredMethod(methodName,parameterTypes);
            Log log2=method.getAnnotation(Log.class);
            System.out.println(log2.operationType());
            System.out.println(log2.operationInfo());

            //简写
            Method targetMethod=((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
            Method method1=clazz.getDeclaredMethod(methodName,targetMethod.getParameterTypes());
            Log log3=method1.getAnnotation(Log.class);
            System.out.println(log3.operationType());
            System.out.println(log3.operationInfo());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result+"11";
    }

    /**
     * 异常后也会进入该方法
     * @param joinPoint
     */
    @After("logPonintCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("=========after=======");
        try {
//可以进行一些资源回收

        }catch (Exception e){

        }
    }

    @AfterReturning(pointcut = "logPonintCut()",returning = "rvt")
    public void doAfterReturning(JoinPoint joinPoint,Object rvt){
        System.out.println("==========afterReturning==========,返回值："+rvt);
    }

    @AfterThrowing(pointcut = "logPonintCut()",throwing = "ex")
    public void doAfterThrowing(JoinPoint joinpoint,Throwable ex){
        System.out.println("==========afterThrowing==========");
        System.out.println(ex.getMessage());
    }

}
