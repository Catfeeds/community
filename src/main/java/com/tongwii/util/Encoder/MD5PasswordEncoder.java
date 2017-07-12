package com.tongwii.util.Encoder;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Md5密码加密
 *
 * @author: Zeral
 * @date: 2017/7/11
 */
public final class MD5PasswordEncoder implements PasswordEncoder {
    private final String SALT;

    public MD5PasswordEncoder() {
        SALT = null;
    }

    /**
     * 使用指定字符串和密码混合生成MD5
     * @param salt 指定字符串
     */
    public MD5PasswordEncoder(final String salt) {
        SALT = salt;
    }

    @Override
    public String encoder(final String rawPass) {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        return passwordEncoder.encodePassword(rawPass, SALT);
    }
}
