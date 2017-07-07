package vr.promulgator.com.newvrmc.model;



import vr.promulgator.com.newvrmc.UrlPath.UrlHttp;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

import vr.promulgator.com.newvrmc.bean.LoginInfo;

/**
 * Created by raytine on 2017/2/11.
 */

public class LoginModel {

        public void getUser(Map<String,String> map, final OnLoginListener listenter){
                OkHttpUtils.postString()
                        .url(UrlHttp.PATH_LOGIN)
                        .content(new Gson().toJson(map))
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))

                        .build()
                        .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response, int id) throws Exception {

                                   Gson g = new Gson();
                                    LoginInfo loginInfo = (LoginInfo) g.fromJson(response.body().string(),LoginInfo.class);
                                    if (loginInfo.getCode() == 0){
                                        listenter.loginSuccess(loginInfo);
                                    }else
                                    {
                                        listenter.loginFailed(loginInfo);
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
    public interface OnLoginListener
    {
        void loginSuccess(LoginInfo user);

        void loginFailed(LoginInfo user);
    }
}
