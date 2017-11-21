package com.tongwii.vm;

import com.tongwii.constant.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
@Setter
@Getter
@ToString
public class LoginVM {

    private String id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @NotNull
    @Size(min = 1, max = 50)
    private String account;

    @NotNull
    private String password;

    private Boolean rememberMe;

    private String deviceId;
}
