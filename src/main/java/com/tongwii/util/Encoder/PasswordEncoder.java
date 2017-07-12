package com.tongwii.util.Encoder;

/**
 * 密码加密接口
 *
 * Author: Zeral
 * Date: 2017/7/11
 */
public interface PasswordEncoder {


    /**
     * 密码加密
     *
     * @author Zeral
     * @param rawPass 原始密码
     * @return String 加密后的密码
     */
    String encoder(String rawPass);
}
