package com.tongwii.core.exception;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    public LoginAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "用户已存在", "userManagement", "userexists");
    }
}
