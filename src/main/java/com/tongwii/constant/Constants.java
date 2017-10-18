package com.tongwii.constant;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final Integer DEFAULT_PAGE_SIZE = 8;

    public static final Integer HUZHU = 1;
    public static final Integer ZUKE = 2;
    public static final Integer MEMBER = 3;

    private Constants() {
    }
}
