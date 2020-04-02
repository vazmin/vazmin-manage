package com.github.vazmin.manage.simple.cache;

import com.github.vazmin.manage.simple.SimpleApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by wangzm on 2020/4/1.
 */
@SpringBootTest(classes = SimpleApplication.class)
public class CacheTest {

    private static final Logger log = LoggerFactory.getLogger(CacheTest.class);


    @Autowired
    private PersonServiceTest personServiceTest;

    @Test
    public void foo() {
        String name = "zhang san";
        int age = 99;
        log.debug("start get personList...");
        List<PersonServiceTest.Person> personList = personServiceTest.getPersonList();
        log.debug("start get person...");
        PersonServiceTest.Person people = personServiceTest.getPerson(name);
        log.debug("start update person...");
        personServiceTest.updateAgeByName(name, 99);
        log.debug("start update person...");
        personServiceTest.updateAgeByName("asd", 100);
        log.debug("start get person...");
        PersonServiceTest.Person people1 = personServiceTest.getPerson(name);
        System.out.println("");
    }

}
