package com.github.vazmin.manage.simple.service;

import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.model.MenuInfo;
import com.github.vazmin.manage.component.service.system.MenuInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Chwing on 2019/8/7.
 */
@Service
public class FooService {
    private static final Logger log = LoggerFactory.getLogger(FooService.class);

    private final MenuInfoService menuInfoService;

    public FooService(MenuInfoService menuInfoService) {
        this.menuInfoService = menuInfoService;
    }

    public void updateMenu() throws ServiceProcessException {
        MenuInfo menu = menuInfoService.get(1L);
        menu.setPath("//");
        String a = null;
        a.indexOf('a');
        menuInfoService.update(menu);
    }
}
