package com.splunk.hollywood.controller;

import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistics")
public class Statistics {

    private Config config;

    @RequestMapping("status")
    @ResponseBody
    public String status() {
        return "OK";
    }

    @RequestMapping("version")
    @ResponseBody
    public String version() {
        return config.getString("hollywood.version");
    }

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }
}
