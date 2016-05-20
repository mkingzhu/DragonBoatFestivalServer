package com.mkingzhu.dragon.server.web.model;

import com.google.gson.annotations.SerializedName;

public class LcUser {
    @SerializedName("openid")
    private String openId;

    @SerializedName("nickname")
    private String nickname;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
