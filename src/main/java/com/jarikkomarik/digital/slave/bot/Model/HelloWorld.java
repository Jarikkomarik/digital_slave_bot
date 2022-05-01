package com.jarikkomarik.digital.slave.bot.Model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class HelloWorld {

    @GetMapping("/hello")
    public String sayHello() {
        return  "Hello world!";
    }
}
