package com.mkingzhu.dragon.server.web.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userinfo")
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
