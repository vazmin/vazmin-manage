package com.github.vazmin.manage.simple.proxy;

/**
 * Created by Chwing on 2019/8/21.
 */
public class Cat implements Animal {
    @Override
    public void say() {
        System.out.println("miao...");
    }

    @Override
    public String foo() {
        return "Mao";
    }
}
