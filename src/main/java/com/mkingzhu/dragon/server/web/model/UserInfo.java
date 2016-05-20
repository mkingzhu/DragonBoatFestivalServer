package com.mkingzhu.dragon.server.web.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("base")
    private LcUser lcUser;
    
    @SerializedName("personal")
    private LcPerson lcPerson;

    public LcUser getLcUser() {
        return lcUser;
    }

    public void setLcUser(LcUser lcUser) {
        this.lcUser = lcUser;
    }

    public LcPerson getLcPerson() {
        return lcPerson;
    }

    public void setLcPerson(LcPerson lcPerson) {
        this.lcPerson = lcPerson;
    }
}