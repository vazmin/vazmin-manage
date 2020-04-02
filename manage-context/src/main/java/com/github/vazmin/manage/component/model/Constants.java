package com.github.vazmin.manage.component.model;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "zh-cn";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String ROLE0 = "ROLE_0-0";

    public interface CacheKey {
        String MANAGE_USER = "manage_user";
        String USER_ROLE = "user_role";
        String USER_PRIVILEGE = "user_privilege";
        String ROLE_PRIVILEGE = "role_privilege";
    }

    public interface Prefix {
        String ROLE = "r_";
        String USER = "u_";
        String GROUP = "g_";
    }

    private Constants() {
    }
}
