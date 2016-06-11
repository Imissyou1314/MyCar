package com.miss.imissyou.mycar.bean;

import java.util.Map;

/**
 * 请求结果Bean
 * Created by Imissyou on 2016/4/16.
 */
public class ResultBean extends BaseBean{


    private boolean serviceResult;      //请求识别标

    private String resultInfo;          //请求结果

    private Map<String, Object> resultParm;          //请求结果


    private boolean userToken;


    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public Map<String, Object> getResultParm() {
        return resultParm;
    }

    public void setResultParm(Map<String, Object> resultParm) {
        this.resultParm = resultParm;
    }

    public boolean isServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(boolean serviceResult) {
        this.serviceResult = serviceResult;
    }

    public Boolean getUserToken() {
        return userToken;
    }

    public void setUserToken(Boolean userToken) {
        this.userToken = userToken;
    }
}
