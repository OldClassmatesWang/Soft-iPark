package com.yunlong.softpark.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class testTest {

    @Autowired
    private test test2;
    @Test
    void findN() {

        System.out.println(test2);
        System.out.println(test2.findN());
        List<city> teste1 = test2.findN();
        System.out.println(teste1);
    }
}