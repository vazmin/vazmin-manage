package com.github.vazmin.manage.simple.mat.web;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class FooControllerTest {

    @Test
    public void getA() {
        FooController fooController = new FooController();
        Class<?> c = fooController.getClass();
        Method[] methods = c.getMethods();

        for(Method method: methods) {
            RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            if (requestMapping != null) {
                System.out.println(Arrays.toString(requestMapping.value()));
            }

        }
    }

    @Test
    public void reqMethod() {
        RequestMethod[] requestMethods = new RequestMethod[]{RequestMethod.GET, RequestMethod.POST};
        String methods = Arrays.toString(requestMethods);
        System.out.println(methods);

    }
}