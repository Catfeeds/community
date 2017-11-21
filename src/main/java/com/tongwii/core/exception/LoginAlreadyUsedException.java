package com.tongwii.core.exception;

import org.zalando.problem.Problem;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    public LoginAlreadyUsedException() {
        super(Problem.DEFAULT_TYPE, "Login already in use", "userManagement", "userexists");
    }
}
