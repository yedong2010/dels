package com.dels.security;

import com.dels.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @description 用户密码加密
 * @author MaytoMarry
 * @time 2017-07-11
 */
public class UPEncode implements PasswordEncoder {
    public String encode(CharSequence arg0) {
        return MD5Util.getMD5Str(MD5Util.getMD5Str(new Object[] { arg0 }) + "mm");
    }

    /**
     *
     * @param arg0
     * @param arg1
     * @return
     */
    public boolean matches(CharSequence arg0, String arg1) {
        if (arg1.equals(encode(arg0))) {
            return true;
        }
        return false;
    }
}
