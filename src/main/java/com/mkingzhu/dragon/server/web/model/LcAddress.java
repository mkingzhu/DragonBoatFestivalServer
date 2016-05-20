package com.mkingzhu.dragon.server.web.model;

import com.google.gson.annotations.SerializedName;

public class LcAddress {
    @SerializedName("province")
    private String province;

    @SerializedName("city")
    private String city;
    
    @SerializedName("outer-address")
    private String outerAddress;

    @SerializedName("inner-address")
    private String innerAddress;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOuterAddress() {
        return outerAddress;
    }

    public void setOuterAddress(String outerAddress) {
        this.outerAddress = outerAddress;
    }

    public String getInnerAddress() {
        return innerAddress;
    }

    public void setInnerAddress(String innerAddress) {
        this.innerAddress = innerAddress;
    }
}
