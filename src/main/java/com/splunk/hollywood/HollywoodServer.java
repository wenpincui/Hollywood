package com.splunk.hollywood;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "com.splunk.hollywood"
        })
@EnableJpaRepositories(basePackages = "com.splunk.hollywood.dao")
@EntityScan(basePackages = "com.splunk.hollywood.model")
@EnableAspectJAutoProxy
public class HollywoodServer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HollywoodServer.class, args);
    }
}

