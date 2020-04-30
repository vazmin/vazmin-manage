package com.github.vazmin.manage.component.vm;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MenuVMTest {

    @Test
    public void childrenTest() {
        Set<MenuVM> menuVMSet = new TreeSet<>(
                Comparator.comparing(MenuVM::getOrder).thenComparing(MenuVM::getTitle));

        MenuVM vmA = new MenuVM();
        vmA.setTitle("Menu A");
        vmA.setOrder(1);
        menuVMSet.add(vmA);

        MenuVM vmB = new MenuVM();
        vmB.setTitle("Menu B");
        vmB.setOrder(1);
        menuVMSet.add(vmB);

        MenuVM vmC = new MenuVM();
        vmC.setTitle("Menu C");
        vmC.setOrder(2);
        menuVMSet.add(vmC);

        assertEquals(menuVMSet.size(), 3);

    }
}