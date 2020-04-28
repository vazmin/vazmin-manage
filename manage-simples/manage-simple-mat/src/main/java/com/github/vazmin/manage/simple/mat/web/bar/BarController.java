package com.github.vazmin.manage.simple.mat.web.bar;

import com.github.vazmin.framework.core.service.ServiceProcessException;
import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import com.github.vazmin.framework.web.support.model.CommandInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangzm on 2020/4/28.
 */
@Module(value = "bar m", order = 1)
@RestController
@RequestMapping("/api/bar")
public class BarController {

    @Command("bar c")
    @GetMapping("")
    public String foo() {
        return "hello, bar";
    }
}
