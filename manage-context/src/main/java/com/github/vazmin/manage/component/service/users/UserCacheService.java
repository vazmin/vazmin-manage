package com.github.vazmin.manage.component.service.users;

import com.github.vazmin.manage.component.model.Constants;
import com.github.vazmin.manage.component.model.users.ManageRole;
import com.github.vazmin.manage.component.model.users.ManageUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wangzm on 2020/4/1.
 */
@Service
public class UserCacheService {
    private static final Logger log = LoggerFactory.getLogger(UserCacheService.class);

    @Autowired
    private ManageUserService manageUserService;

    // TODO: add cache put
    @Cacheable(cacheNames = Constants.CacheKey.MANAGE_USER, key = "#username", condition = "#result != null")
    public ManageUser getByUsername(String username) {
        return manageUserService.getByUsername(username);
    }

    @CachePut(cacheNames = Constants.CacheKey.USER_PRIVILEGE, key = "#id")
    public Set<String> updateUserPrivilegeCache(Long id, String[] privileges) {
        return new HashSet<>(Arrays.asList(privileges));
    }

    @CachePut(cacheNames = Constants.CacheKey.ROLE_PRIVILEGE, key = "#id")
    public Set<String> updateRolePrivilegeCache(Long id, String[] privileges) {
        return new HashSet<>(Arrays.asList(privileges));
    }
}
