    package com.dels.security;

    import com.dels.model.monitor.BaseDataRegion;
    import com.dels.model.sys.*;
    import com.dels.service.IOperateLogService;
    import com.dels.service.ISysMngService;
    import com.dels.utils.LogDescription;
    import com.dels.utils.Utils;
    import org.aspectj.lang.JoinPoint;
    import org.aspectj.lang.annotation.AfterReturning;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Before;
    import org.aspectj.lang.reflect.MethodSignature;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import java.lang.annotation.Annotation;
    import java.lang.reflect.Method;


    /**
     * 切面记录日志信息
     * controller 层权限控制
     * @author: l13595
     * @date:2017-01-10
     */
    @Component
    @Aspect
    public class AuditLog {
        private org.slf4j.Logger logger = LoggerFactory.getLogger(AuditLog.class);

        @Autowired
        IOperateLogService service;
        @Autowired
        ISysMngService sysMngService;

        /**
         * @description:切面日志类
         * @author: l13595
         * @param:
         * @time: 2017/1/13 17:25
         */
        @AfterReturning("execution(* com.h3c.bigdata.dms.service..*(..))")
        public void logger(JoinPoint  joinPoint){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Annotation a = method.getAnnotation(LogDescription.class);
            if(null!=a){
                LogDescription loginfo =(LogDescription) a;
                Object[] args=joinPoint.getArgs(); //获取方法参数
                String log=constructLog(loginfo,args);
                logger.info("【日志】："+log);
                OperateLog operateLog = new OperateLog();
                operateLog.setId(Utils.getUuid());
                operateLog.setUser_name(Utils.getCurUserName());
                operateLog.setOperation(log);
                operateLog.setClassName(joinPoint.getTarget().getClass().getName());
                operateLog.setMethodName(joinPoint.getSignature().getName());
                operateLog.setIpAdress(Utils.getCurUserIp());
                service.saveLog(operateLog);
            }
        }

        /**
         * @description: 对controller层访问的权限控制，根据当前用户权限，对比数据库存储的权限，限制没有权限的用户访问
         * @author: l13595
         * @param :
         * @return:
         * @time: 2017/3/21 9:35
         */
        @Before("within(com.h3c.bigdata.dms.controller..*)")
        public void controllerAspect(){
            //TODO 控制层权限
            /** l13595 2017/3/21 9:40 **/
        }

        /**
         * @description: controller层的切面注解
         * @author: l13595
         * @param joinPoint:  JoinPoint
         * @time: 2017/4/1 14:32
         */
        @AfterReturning("within(com.dels.controller.sysMng.SysMngController)")
        public void logInAction(JoinPoint  joinPoint){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Annotation a = method.getAnnotation(LogDescription.class);
            if(null!=a){
                LogDescription loginfo =(LogDescription) a;
                Object[] args=joinPoint.getArgs(); //获取方法参数
                String log=constructLog(loginfo,args);
                logger.info("【日志】："+log);
                OperateLog operateLog = new OperateLog();
                operateLog.setId(Utils.getUuid());
                operateLog.setUser_name(Utils.getCurUserName());
                operateLog.setOperation(log);
                operateLog.setClassName(joinPoint.getTarget().getClass().getName());
                operateLog.setMethodName(joinPoint.getSignature().getName());
                operateLog.setIpAdress(Utils.getCurUserIp());
                service.saveLog(operateLog);
            }
        }

        /**
         * @description: 组装需要显示的日志
         * @author: l13595
         * @param log: 注解内容
         * @param args: 方法的所有参数
         * @return: 组装完成的日志信息
         * @time: 2017/1/12 17:01
         */
        private String constructLog(LogDescription log,Object[] args){
            StringBuffer logInfo = new StringBuffer();
            logInfo.append(log.description());
            if(log.pOrder()!=-1){
                switch (log.pType()){
                    case "User":
                        User user = (User) args[log.pOrder()];
                        if(null != user && null != user.getUname()){
                            logInfo.append(":");
                            logInfo.append(user.getUname());
                        }
                        break;
                    case "Role":
                        Role role = (Role) args[log.pOrder()];
                        if(null != role && null != role.getRol_name()){
                            logInfo.append(":");
                            logInfo.append(role.getRol_name());
                        }
                        break;
                    case "PlanInfo":
                        PlanInfo plan = (PlanInfo) args[log.pOrder()];
                        if(null != plan && null != plan.getName()){
                            logInfo.append(":");
                            logInfo.append(plan.getName());
                        }
                        break;
                    case "FileInfo":
                        FileInfo f = (FileInfo) args[log.pOrder()];
                        if(null!=f && null!=f.getFileType()){
                            logInfo.append(":");
                            logInfo.append(f.getFileType());
                        }
                        break;
                    case "addFileType":
                        FileInfo f2 = (FileInfo) args[log.pOrder()];
                        if(null!=f2 && null!=f2.getFileType()){
                            logInfo.append(":");
                            logInfo.append(f2.getFileType());
                        }
                        break;
                    case "file":
                        FileInfo f3 = (FileInfo) args[log.pOrder()];
                        if(null != f3 && null != f3.getFileName()){
                            logInfo.append(":");
                            logInfo.append(f3.getFileName());
                        }
                        break;
                    case "baseData":
                        BaseDataRegion baseData = (BaseDataRegion) args[log.pOrder()];
                        if(null != baseData && null != baseData.getXZQHDM()){
                            logInfo.append(":");
                            logInfo.append(baseData.getXZQHDM());
                        }
                        break;
                    default:
                        logInfo.append(":");
                        logInfo.append(args[log.pOrder()]);
                }
            }
            return logInfo.toString();
        }
    }
