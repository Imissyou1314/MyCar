package com.miss.imissyou.mycar.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.bean.BaseBean;
import com.miss.imissyou.mycar.bean.ResultBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Gson 的工具类
 * Created by Imissyou on 2016/4/19.
 */
public class GsonUtils {

    private  static Gson mGson;
    private static ResultBean resultBean;
    private static String key;
    private static Class<BaseBean[]> clazz;

    private GsonUtils() {

    }

    /**
     * 获取Gson的单例
     * @return
     */
    public static Gson Instance() {
        if (null == mGson) {
            mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
        return mGson;
    }

    /**
     * Json转 实体类
     * @param jsonStr  Json数据
     * @param baseBean extends BaseBean
     * @return  <Class extends BaseBean></Class>
     */
    public static BaseBean getBean(String jsonStr, BaseBean baseBean) {
        if (StringUtil.isEmpty(jsonStr))
            return null;
        LogUtils.d("转型的类是" + baseBean.getClass().toString());
        BaseBean result = (BaseBean) Instance().fromJson(jsonStr, baseBean.getClass());
        return result;
    }

    /**
     * 对象转JSON
     * @param object
     * @return String jsonStr 转型后的Json
     */
    public static String getJsonStr(Object object) {

        if (null == object)
            return null;
        LogUtils.d("转型的是:::" + object.toString());

        String jsonStr = Instance().toJson(object);
        if (jsonStr != null)
            return jsonStr;
        LogUtils.d("GSON 转型失败");
        return "";
    }

    /**
     * 获取返回的值
     * @param resultBean
     * @param key
     * @param <T>
     * @return List<T></T>
     */
    public static <T extends BaseBean> List<T> getParams(ResultBean resultBean, String key, Class<T[]> clazz) {

        String tempStr = GsonUtils.Instance().toJson(resultBean.getResultParm().get(key));
        LogUtils.d("List Json" + tempStr);
        if (clazz != null && tempStr != null) {
            T[] resultList = GsonUtils.Instance().fromJson(tempStr, clazz);
            if (resultList == null)
                return null;
            return Arrays.asList(resultList);
        } else {
            return null;
        }
    }

    /**
     * 获取返回的值
     * @param resultBean
     * @param key
     * @param <T>
     * @return T</T>
     */
    public static <T extends BaseBean> T getParam(ResultBean resultBean, String key, Class<T> clazz) {
        String tempStr = GsonUtils.Instance().toJson(resultBean.getResultParm().get(key));
        LogUtils.d("List Json" + tempStr);
        T result = GsonUtils.Instance().fromJson(tempStr, clazz);
        return result;
    }

    /**
     * 获取ResultBean
     * @param t
     * @return
     */
    public static ResultBean getResultBean(String t) {
        if (null == t || t.equals(""))
            return null;
        ResultBean resultBean = GsonUtils.Instance().fromJson(t, ResultBean.class);
        return resultBean;
    }
}
