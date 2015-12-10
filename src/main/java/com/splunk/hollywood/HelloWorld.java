package com.splunk.hollywood;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.splunk.hollywood"
        })
@Controller
public class HelloWorld {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HelloWorld.class, args);
    }
}

