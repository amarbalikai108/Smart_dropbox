package com.example.smartdropbox.registration.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegistrationModel implements Serializable {
        @SerializedName("name")
    public String userName="";
    @SerializedName("contact")
    public String phoneNumber="";
    /*@SerializedName("Route")
    public String route;*/
    @SerializedName("id")
    public String empId="";
    @SerializedName("class")
    public String userClass="user";
    @SerializedName("ver")
    public String appVersion="";
    @SerializedName("sdk")
    public String sdkVersion="";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

  /*  public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }*/

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }
}
