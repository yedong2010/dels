package com.dels.security;

import com.dels.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @description 用户密码加密
 *        修改 l13595 2017/4/26 11:09 :新增和修改的用户由两层加密改为四层加密，保存数据库
 * @author z11595
 * @time 2016年12月22日 下午4:06:57
 */
public class UPEncode implements PasswordEncoder {
    public String encode(CharSequence arg0) {
        return MD5Util.getMD5Str(MD5Util.getMD5Str(new Object[] { arg0 }) + "sjjc");
    }

    /**
     *
     * @param arg0
     * @param arg1
     * @return
     */
    public boolean matches(CharSequence arg0, String arg1) {
//        if (arg1.equals(encode(arg0))) {
        if (arg1.equals(arg0)) {
            return true;
        }
        return false;
    }
}
