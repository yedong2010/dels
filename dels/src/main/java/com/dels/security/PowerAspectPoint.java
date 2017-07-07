package com.dels.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.dels.utils.BasicConstants;

/**
 * 
 * @description 数据权限拦截 切面类
 * @author z11595
 * @time 2016年12月22日 下午4:05:26
 */
@Aspect
public class PowerAspectPoint {
    Logger logger = LoggerFactory.getLogger(PowerAspectPoint.class);

    @SuppressWarnings("unused")
    /**
     * 
     * @description 定义切入点 这个方法的名字就是改切入点的id
     * @author z11595
     * @time 2017年1月7日 下午1:45:49
     */
    @Pointcut("execution(* com.h3c.bigdata.dms.service.impl.monitor.*.*(..,java.util.List))")
    private void allMethod() {
    }

    /**
     * 
     * @description 针对指定的切入点表达式选择的切入点应用前置通知
     * @author z11595
     * @param call
     *            切点
     * @return 返回当前用户归属的省级部门或地市名称
     * @time 2017年1月7日 下午1:46:32
     */
    @Before("execution(* com.h3c.bigdata.dms.service.impl.monitor.*.*(..,java.util.List,*))")
    public String before(JoinPoint call) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String c = getName(userDetails.getAuthorities());
        return c;
    }

    /**
     * 
     * @description 访问命名切入点来应用后置通知
     * @author z11595
     * @time 2017年1月7日 下午1:47:39
     */
    @AfterReturning("allMethod()")
    public void afterReturn() {
        logger.info("【注解-后置通知】:方法正常结束了");
    }

    /**
     * 
     * @description 应用最终通知
     * @author z11595
     * @time 2017年1月7日 下午1:47:52
     */
    @After("allMethod()")
    public void after() {
        logger.info("【注解-最终通知】:不管方法有没有正常执行完成," + "一定会返回的");
    }

    // 应用异常抛出后通知
    @AfterThrowing("allMethod()")
    public void afterThrowing() {
        logger.info("【注解-异常抛出后通知】:方法执行时出异常了");
    }

    /**
     * 
     * @description 应用周围通知
     * @author z11595
     * @param call
     *            切点
     * @return 拦截修改参数状态
     * @throws Throwable
     *             报错信息
     * @time 2017年1月7日 下午1:44:15
     */
    @Around("allMethod()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable {
        Object result = null;
        String city = this.before(call);// 相当于前置通知
        Object[] args = call.getArgs();
        try {
            Integer len = args.length;
            if (len > 0) {
                if (!StringUtils.isEmpty(city)) {
                    List<String> cp = new ArrayList<String>();
                    cp.add(city);
                    args[len - 1] = cp;
                    logger.info("【注解-现参数cparams】：" + args[len - 1]);
                }
            }
            String className = call.getTarget().getClass().getName();
            String methodName = call.getSignature().getName();
            logger.info("【注解-前置通知】:" + className + "类的" + methodName + "方法开始了");
            result = call.proceed(args);
        } catch (Throwable e) {
            throw e;
        } finally {
        }
        return result;
    }

    /**
     * 
     * @description 通过spring security的用户缓存得到当前用户的归属部门信息
     * @author z11595
     * @param collection
     * @return
     * @time 2017年1月7日 下午1:49:54
     */
    public String getName(Collection<? extends GrantedAuthority> collection) {
        for (GrantedAuthority g : collection) {
            String g_str = g.toString();
            g_str.replaceAll(" ", "");
            if (g_str.indexOf(BasicConstants.AUTH_EX_STR) > -1) {
                return g_str.replaceAll(BasicConstants.AUTH_EX_STR, "");
            }
        }
        return null;
    }
}
