package com.example.rin.demo;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {

        double longitude_user =  55.7539;
        double latitude_user = 37.6208;
        double longitude_product = 59.9398;
        double latitude_product = 30.3146;

        double length = (2 * 6371 * (1 /
                Math.sin(Math.sqrt(
                        Math.sin((longitude_product - longitude_user) / 2) * Math.sin((longitude_product - longitude_user) / 2) +
                        Math.cos(longitude_product) * Math.cos(longitude_user) *
                        Math.sin((latitude_product - latitude_user) / 2) * Math.sin((latitude_product - latitude_user) / 2)
                ))));

        System.out.println(length);

    }

}
