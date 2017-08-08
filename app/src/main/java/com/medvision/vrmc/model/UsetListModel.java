package com.medvision.vrmc.model;

import com.medvision.vrmc.UrlPath.UrlHttp;
import com.medvision.vrmc.bean.UserList;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by raytine on 2017/4/14.
 */

public class UsetListModel {
    public void  getUserList(Map<String,String> map, final GetUserListLisencer getUserListLisencer){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_GETUSERLIST)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        Gson g = new Gson();
                        UserList loginInfo = (UserList) g.fromJson(response.body().string(),UserList.class);
                        if (loginInfo.getCode() == 0){
                            getUserListLisencer.GetUserListSuccess(loginInfo);
                        } else if(loginInfo.getCode() == 95 ||loginInfo.getCode() == 96 ||loginInfo.getCode() ==97||loginInfo.getCode() ==98){
                            getUserListLisencer.tokenChange();
                        }
                        else
                        {
                            getUserListLisencer.GetUserListFailed(loginInfo.getMessage());
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
    public interface GetUserListLisencer{
        void GetUserListSuccess(UserList user);
        void tokenChange();
        void GetUserListFailed(String user);
    }
}
