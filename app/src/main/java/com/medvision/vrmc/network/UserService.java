package com.medvision.vrmc.network;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

import com.cs.networklibrary.entity.NoData;
import com.medvision.vrmc.bean.CertificationInfo;
import com.medvision.vrmc.bean.CertificationStatus;
import com.medvision.vrmc.bean.CityInfo;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.LoginInfos;
import com.medvision.vrmc.bean.NewsInfo;
import com.medvision.vrmc.bean.ProfessionalTitle;
import com.medvision.vrmc.bean.VerifyImg;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.CollectionListReq;
import com.medvision.vrmc.bean.requestbody.RegisterReq;
import com.medvision.vrmc.bean.requestbody.SmsReq;
import com.medvision.vrmc.bean.requestbody.UserReq;
import com.medvision.vrmc.bean.requestbody.VerifyInfoReq;
import com.medvision.vrmc.bean.requestbody.findReq;

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
    //注册
    @POST("appControlDoctor/register")
    Observable<HttpResult<NoData>> requestRegister(@Body RegisterReq registerReq);
    //发送验证码
    @POST("appControlDoctor/sendCode")
    Observable<HttpResult<String>> requestSms(@Body SmsReq smsReq);
    //找回密码
    @POST("appControlDoctor/resetPassword")
    Observable<HttpResult<String>> findPassword(@Body findReq smsReq);
    //获取认证状态1:未认证2:待认证3:认证失败4:认证通过9:停诊
    @POST("appControlDoctor/authenticationStatus")
    Observable<HttpResult<CertificationStatus>> requestCertificateStatus(@Body BaseReq smsReq);
    //获取认证信息
    @POST("appControlDoctor/authInfo")
    Observable<HttpResult<CertificationInfo>> requestCertificateInfo(@Body BaseReq smsReq);
    //通过token  获取城市联动信息
    @POST("appControlDoctor/getRegion")
    Observable<HttpResult<List<CityInfo>>> requestCityInfo(@Body BaseReq baseReq);
    //获取全部职称d
    @POST("appControlDoctor/professionalTitles")
    Observable<HttpResult<List<ProfessionalTitle>>> requestTitleInfo(@Body BaseReq baseReq);
    //图片上传
    @POST("appControlDoctor/imageUpload")
    Observable<HttpResult<VerifyImg>> uploadVerifyImg(@Body MultipartBody body);
    //验证信息上传
    @POST("appControlDoctor/auth")
    Observable<HttpResult<NoData>> uploadVerifyInfo(@Body VerifyInfoReq verifyInfoReq);
    /**
     * 获取新闻资讯
     */
    @POST("appControlDoctor/getConsulting")
    Observable<HttpResult<List<NewsInfo>>> getNewsInfo(@Body BaseReq baseReq);
}
