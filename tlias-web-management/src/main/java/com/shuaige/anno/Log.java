package com.shuaige.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 */
@Target(ElementType.METHOD)//  注解作用在方法上
@Retention(RetentionPolicy.RUNTIME)// 运行时保留
public @interface Log {
}
