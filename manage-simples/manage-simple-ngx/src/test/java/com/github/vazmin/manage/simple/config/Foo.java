package com.github.vazmin.manage.simple.config;

/**
 * Created by Chwing on 2019/8/19.
 */
public class Foo {
    private static Foo foo= new Foo();

    public static Foo getFoo() {
        return foo;
    }
}
