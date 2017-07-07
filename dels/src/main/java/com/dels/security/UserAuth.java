package com.dels.security;

import com.dels.service.IUserService;
import com.dels.utils.BasicConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @description 用户认证处理
 * @author z11595
 * @time 2016年12月22日 下午4:07:13
 */
public class UserAuth implements UserDetailsService {
    @Autowired
    IUserService UserService;

    /**
     * 通过用户名称查询数据库当前用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        com.dels.model.sys.User cuentu = this.UserService.selectUserByName(arg0);
        if (null == cuentu) {
            throw new IllegalArgumentException("用户名或密码错误!");
        }
        if (cuentu.getStatus().intValue() != 0) {
            throw new LockedException("账户已被禁用,请联系管理员!");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        String op = StringUtils.isEmpty(cuentu.getDepartment()) ? BasicConstants.AUTH_EX_STR : BasicConstants.AUTH_EX_STR + cuentu.getDepartment();
        authorities.add(new GrantedAuthorityImpl(op));

        /* UserInfo替换User,UserInfo中增加了用户的ip地址 l13595 2017/4/15 14:38*/
        UserInfo user = new UserInfo(cuentu.getUname(), cuentu.getPasswd(), authorities);

        return user;
    }

}
