package com.medvision.vrmc.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.LoginInfos;
import com.medvision.vrmc.bean.requestbody.CollectionListReq;
import com.medvision.vrmc.bean.requestbody.UserReq;

/**
 * Created by raytine on 2017/7/11.
 */

public interface UserService {
    //登陆
    @POST("appControlVrRoom/login")
    Observable<HttpResult<LoginInfos>> requestLogin(@Body UserReq userReq);
    //获取个人收藏列表
    @POST("appControlDoctor/contentCollect/search")
    Observable<HttpResult<List<HomeContent>>> requestCollectionList(@Body CollectionListReq collectionListReq);
}
