package com.miss.imissyou.mycar.util.zxing.camera;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
import com.miss.imissyou.mycar.util.GsonUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * 上传图片
 * Created by Imissyou on 2016/6/15.
 */
public class UploadFile {

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("imge/png");            //选择png类型文件
    private final OkHttpClient client = new OkHttpClient();     //创建OKhttp实例


    public UploadFile(String path, String url, Callback callback) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);


        if (null != path && !path.equals("")) {
            LogUtils.d("图片地址:" + path + ">>发往地址:" + url);
            builder.addFormDataPart("upload", null, RequestBody.create(MEDIA_TYPE_PNG, new File(path)));
        } else {
            return;
        }

        RequestBody requestBody = builder.build();

        final Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * 多文件上传
     * @param path  对应的上传数据
     * @param url   发往地址
     * @param callback   回调
     */
    public UploadFile(Map<String,String> path, String url, Callback callback) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Iterator item = path.entrySet().iterator(); item.hasNext();) {
            Map.Entry entry = (Map.Entry) item.next();
            builder.addFormDataPart(entry.getKey().toString(), null,
                    RequestBody.create(MEDIA_TYPE_PNG, new File(entry.getValue().toString())));

        }

        RequestBody requestBody = builder.build();
        final  Request request = new Request.Builder().url(url).post(requestBody).build();

        client.newCall(request).enqueue(callback);
    }
}
