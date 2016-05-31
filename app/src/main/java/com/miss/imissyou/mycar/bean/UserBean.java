package com.miss.imissyou.mycar.bean;

/**
 * 用户实体Bean
 * Created by Imissyou on 2016/3/28.
 */
public class UserBean extends BaseBean {

    /**用户ID*/
    private Long id;
    /**用户名*/
    private String username;
    /**用户真名*/
    private String realName;
    /**用户账号*/
    private String loginid;
    /**用户密码*/
    private String password;
    /**用户手机*/
    private String phone;
    /**亲人手机号*/
    private String relatedPhone;
    /**安全密码*/
    private String safePassword;
    /**用户头像*/
    private String userImg;

    public String getSafePassword() {
        return safePassword;
    }

    public void setSafePassword(String safePassword) {
        this.safePassword = safePassword;
    }

    public void setRelatedPhone(String relatedPhone) {
        this.relatedPhone = relatedPhone;
    }


    public String getRelatedPhone() {
        return relatedPhone;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

