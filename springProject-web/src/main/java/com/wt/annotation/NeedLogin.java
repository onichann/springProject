package com.wt.annotation;
import java.lang.annotation.*;

/**
 *是否需要判断登录，默认为true需要判断登录，设定为false的情况下不判断登录
 */
@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {
    boolean value() default true;
}
