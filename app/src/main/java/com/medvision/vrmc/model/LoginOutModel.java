package com.medvision.vrmc.model;

import com.medvision.vrmc.bean.NoData;
import com.medvision.vrmc.UrlPath.UrlHttp;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by raytine on 2017/4/25.
 */

public class LoginOutModel {

    public void getUser(Map<String,String> map, final OnLoginOutListener listenter){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_LOUUT)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {

                        Gson g = new Gson();
                        NoData loginInfo = (NoData) g.fromJson(response.body().string(),NoData.class);
                        if (loginInfo.getCode() == 0){
                            listenter.loginoutSuccess(loginInfo);
                        }else
                        {
                            listenter.loginoutFailed(loginInfo);
                        }
                        return null;
                    }


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });

    }
    public interface OnLoginOutListener
    {
        void loginoutSuccess(NoData user);

        void loginoutFailed(NoData user);
    }
}
