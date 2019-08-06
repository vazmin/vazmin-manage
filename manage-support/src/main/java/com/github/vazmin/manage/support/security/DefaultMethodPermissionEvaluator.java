package com.github.vazmin.manage.support.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 默认方法权限计算实现类,用于spring security method授权中的 hasPermission()
 *
 */
public class DefaultMethodPermissionEvaluator implements PermissionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(DefaultMethodPermissionEvaluator.class);

    @Override
    public boolean hasPermission(
            Authentication authentication, Object targetObj, Object permission) {
        log.debug("验证权限 targetObj=" + targetObj + ",permission=" + permission);
        return false;
    }

    @Override
    public boolean hasPermission(
            Authentication authentication, Serializable serializable,
            String s, Object o) {
        log.debug("验证权限 s=" + s + ",o=" + o);
        return false;
    }
}
