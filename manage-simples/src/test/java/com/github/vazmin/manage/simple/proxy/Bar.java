package com.github.vazmin.manage.simple.proxy;

/**
 * Created by Chwing on 2019/8/22.
 */
public class Bar extends Foo {


    public void z() {
        System.out.println("foo");
    }

    public static void main(String[] args) {
        Foo foo = new Bar();
        ((Bar) foo).z();
    }
    public void bar() {

    }
}
