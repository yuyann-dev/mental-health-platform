package com.example.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    //密码加密
    public String encrypt(String password) {
        return DigestUtils.md5Hex(password);
    }
}
