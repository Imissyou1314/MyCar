package com.miss.imissyou.mycar.bean;

/**
 * 用户实体Bean
 * Created by Imissyou on 2016/3/28.
 */
public class UserBean extends BaseBean {

    /**用户名*/
    private String userName;

    /**用户账号*/
    private String account;
    /**用户密码*/
    private String password;
    /**用户ID*/
    private String userID;
    /**用户真名*/
    private String realName;
    /**用户手机*/
    private String phoneNumber;
    /**亲人手机号*/
    private String dersesPhone;
    /**车数量*/
    private int carNumber;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getDersesPhone() {
        return dersesPhone;
    }

    public void setDersesPhone(String dersesPhone) {
        this.dersesPhone = dersesPhone;
    }
}

