package com.splunk.hollywood.controller;

import com.splunk.hollywood.dto.ServerStatus;
import com.splunk.hollywood.dto.ServerVersion;
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
    public ServerStatus status() {
        return new ServerStatus();
    }

    @RequestMapping("version")
    @ResponseBody
    public ServerVersion version() {
        String version = config.getString("hollywood.version");
        return new ServerVersion(version);
    }

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }
}
