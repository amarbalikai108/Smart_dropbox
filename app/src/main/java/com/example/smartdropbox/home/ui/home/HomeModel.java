package com.example.smartdropbox.home.ui.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeModel implements Serializable
{
    @SerializedName("id")
    public String deviceId="";
    @SerializedName("BoxID")
    public String boxId="";

    @SerializedName("name")
    public String name="";

    @SerializedName("class")
    private String userClass="device";

    @SerializedName("lat")
    private Double latitude;

    @SerializedName("lng")
    private Double longitude;

    public String address="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
