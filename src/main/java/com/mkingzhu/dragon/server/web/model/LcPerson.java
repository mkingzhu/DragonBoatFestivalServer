package com.mkingzhu.dragon.server.web.model;

import com.google.gson.annotations.SerializedName;

public class LcPerson {
    @SerializedName("mobile")
    private String mobile;

    @SerializedName("username")
    private String username;
    
    @SerializedName("address")
    private LcAddress address;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LcAddress getAddress() {
        return address;
    }

    public void setAddress(LcAddress address) {
        this.address = address;
    }
}
