package com.dels.service;

import com.dels.model.sys.User;
/**
 * 
 * @description 用户服务接口
 * @author z11595
 * @time 2016年12月22日 下午4:09:39
 */
public abstract interface IUserService {
    /**
     * 
     * @description 通过用户名称查找当前用户
     * @author: z11595
     * @param paramString 用户名称
     * @return 用户实体类信息
     * @time 2016年12月22日 下午4:09:55
     */
    public abstract User selectUserByName(String paramString);

     /**
      * @description: 通过手机号码查找当前用户
      * @author: l13595
      * @param phoneNum: 用户手机号
      * @return: 用户实体类信息
      * @time: 2017/5/25 15:57
      */
    public abstract User selectUserByPhone(String phoneNum);
}
