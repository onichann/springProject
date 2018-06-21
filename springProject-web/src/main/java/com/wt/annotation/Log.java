package com.wt.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 *  operationType  操作类型 add / delete
 *  operationInfo 其他信息
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Log {
    public String operationType() default "";
    public String operationInfo() default "";
}
