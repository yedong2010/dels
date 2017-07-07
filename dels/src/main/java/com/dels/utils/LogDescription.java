package com.dels.utils;

import java.lang.annotation.*;

/**
 * Created by l13595 on 2017/1/12.
 * 定义日志注解类
 * 由于需要将一些参数的值显示到日志中，如新增了某个用户，需要获取用户的名称。所以添加pOrder、pType属性
 * pOrder表示第几个参数的值需要显示到日志中
 * pType表示需要显示的参数的类型。两个同时存在的时候(pOrder不为-1)，从切面中方法中获取需要显示的参数值
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogDescription {

    /**
     * @description:日志的描述
     * @author: l13595
     * @time: 2017/1/12 16:36
     */
    String description() default "";

    /**
     * @description:要获取的参数在该方法所有参数中的排序
     * @author: l13595
     * @time: 2017/1/12 16:35
     */
    int pOrder() default -1;

    /**
     * @description: 参数类型【String,User,Role】
     * @author: l13595
     * @time: 2017/1/12 16:49
     */
    String pType() default "";

}
