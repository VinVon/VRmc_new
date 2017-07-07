package vr.promulgator.com.newvrmc.model;

import vr.promulgator.com.newvrmc.UrlPath.UrlHttp;
import vr.promulgator.com.newvrmc.bean.NoData;
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

public class AddTaskModel {
    public void AddTask(Map<String,Object> map, final AddTaskListLisencer getUserListLisencer){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_ADDTASK)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        Gson g  =new Gson();
                        NoData b = (NoData) g.fromJson(response.body().string(),NoData.class);
                        if (b.isSuccess()){
                            if(b.getCode() == 95 ||b.getCode() == 96 ||b.getCode() ==97||b.getCode() ==98){
                                getUserListLisencer.tokenChange();
                            }else if (b.getCode() == 0){
                                getUserListLisencer.AddTaskSuccess();
                            }else{
                                getUserListLisencer.AddTaskFailed(b.getMessage());
                            }
                        }else{
                            getUserListLisencer.AddTaskFailed(b.getMessage());
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
    public interface AddTaskListLisencer{
        void AddTaskSuccess();
        void tokenChange();
        void AddTaskFailed(String user);
    }
}
