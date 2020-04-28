package com.github.vazmin.manage.simple.mat.web.bar.baz;

import com.github.vazmin.framework.web.support.annotation.Command;
import com.github.vazmin.framework.web.support.annotation.Module;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangzm on 2020/4/28.
 */
@Module(value = "baz m", order = 1)
@RestController
@RequestMapping("/api/baz")
public class BazController {

    @Command("bar z")
    @GetMapping("")
    public String foo() {
        return "hello, bar";
    }
}
