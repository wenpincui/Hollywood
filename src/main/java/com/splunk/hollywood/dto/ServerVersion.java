package com.splunk.hollywood.dto;

public class ServerVersion {
    public ServerVersion(String version) {
        this.version = version;
    }

    public ServerVersion() {
    }

    String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
