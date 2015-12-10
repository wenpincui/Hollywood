package com.splunk.hollywood.controller;

@RestController
@RequestMapping(value = "/")
public class Server {

    private Config config;

    @RequestMapping("status")
    @ResponseBody
    public String status() {
        return "OK";
    }

    @RequestMapping("version")
    @ResponseBody
    public String version() {
        return config.getString("version");
    }

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }
}
