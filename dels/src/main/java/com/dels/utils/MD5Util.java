package com.dels.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @description MD5加密工具类
 * @author z11595
 * @time 2016年12月22日 下午4:41:58
 */
public class MD5Util {
    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }

    public static String getMD5Str(Object... objects) {
        StringBuilder stringBuilder = new StringBuilder();
        Object[] arrayOfObject = objects;
        int j = objects.length;
        for (int i = 0; i < j; i++) {
            Object object = arrayOfObject[i];
            stringBuilder.append(object.toString());
        }
        return getMD5Str(stringBuilder.toString());
    }
}
