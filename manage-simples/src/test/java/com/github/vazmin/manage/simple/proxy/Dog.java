package com.github.vazmin.manage.simple.proxy;

/**
 * Created by Chwing on 2019/8/21.
 */
public class Dog implements Animal {
    @Override
    public void say() {
        System.out.println("wang...");
    }

    @Override
    public String foo() {
        return "Gou";
    }
}
