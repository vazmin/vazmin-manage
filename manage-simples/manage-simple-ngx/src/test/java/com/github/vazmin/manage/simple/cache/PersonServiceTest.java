package com.github.vazmin.manage.simple.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wangzm on 2020/4/1.
 */
@Service
@CacheConfig(cacheNames = "person")
public class PersonServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceTest.class);

    List<Person> personList = new ArrayList<>();

    public PersonServiceTest() {
        personList.add(new Person("zhang san", 10));
        personList.add(new Person("li si", 15));
        personList.add(new Person("wang wu", 20));
    }

    @Cacheable("personList")
    public List<Person> getPersonList() {
        log.debug("get Person List...");
        return personList;
    }

    @Cacheable()
    public Person getPerson(String name) {
        log.debug("get Person...");
        return  personList.stream()
                .filter(person -> person.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found user by name: " + name));
    }

    @CachePut(key = "#name", condition = "#result != null")
    public Optional<Person> updateAgeByName(String name, Integer age) {
        log.debug("update Person...");
        Optional<Person> optionalPerson = personList.stream()
                .filter(person -> person.name.equals(name))
                .findFirst();
        optionalPerson.ifPresent(person -> person.setAge(age));
        return optionalPerson;
    }


    static class Person {
        private String name;
        private Integer age;

        public Person() {
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
