package com.github.vazmin.manage.simple.proxy;

import org.junit.jupiter.api.Test;

/**
 * Created by Chwing on 2019/8/21.
 */
public class DynamicAgentTest {

    @Test
    public void catTest() {
        Animal animal = (Animal) DynamicAgent.agent(Animal.class, new Cat());
        animal.say();
        animal.foo();
    }
}
