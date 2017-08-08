package com.medvision.vrmc.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import com.medvision.vrmc.bean.FilterDisease;
import com.medvision.vrmc.bean.FilterType;
import com.medvision.vrmc.bean.HistoryPrescriptionInfo;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.MyPatientDetil;
import com.medvision.vrmc.bean.MyPatients;
import com.medvision.vrmc.bean.PrescriptionInfo;
import com.medvision.vrmc.bean.SingerContentInfo;
import com.medvision.vrmc.bean.SingerPrescriptionInfo;
import com.medvision.vrmc.bean.requestbody.AddPatientreq;
import com.medvision.vrmc.bean.requestbody.AddPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.GetSingerPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.HistoryPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.HomeContentReq;
import com.medvision.vrmc.bean.requestbody.ModifPatientreq;
import com.medvision.vrmc.bean.requestbody.Patientreq;

import com.medvision.vrmc.bean.AddPatientInfo;
import com.medvision.vrmc.bean.ModifPatientInfo;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;

/**
 * Created by raytine on 2017/7/11.
 */

public interface ContentService {
    /**
     * 获取病症
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/diseaseList")
    Observable<HttpResult<List<FilterDisease>>> getFilterDisease(@Body BaseReq baseReq);
    /**
     * 添加患者
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/addPatient")
    Observable<HttpResult<AddPatientInfo>> addPatient(@Body AddPatientreq baseReq);
    /**
     * 获取我的患者
     *
     */
    @POST("appControlDoctor/myPatients")
    Observable<HttpResult<List<MyPatients>>> getMyPatient(@Body BaseReq baseReq);

    /**
     * 我的患者详情
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/patientInformations")
    Observable<HttpResult<MyPatientDetil>> getMyPatientDetil(@Body Patientreq baseReq);

    /**
     * 修改患者信息
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/modifyPatientInformations")
    Observable<ModifPatientInfo> modifMyPatientDetil(@Body ModifPatientreq baseReq);

    /**
     * 获取筛选病种列
     *
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/content/disease")
    Observable<HttpResult<List<FilterType>>> getFilterDiseases(@Body BaseReq baseReq);
    /**
     * 搜索疗法内容
     *
     * @param homeContentReq
     * @return
     */
    @POST("appControlDoctor/content/search")
    Observable<HttpResult<List<HomeContent>>> getSearchContent(@Body HomeContentReq homeContentReq);
    /**
     * 获取单个疗法内容
     *
     */
    @POST("appControlDoctor/content/info")
    Observable<HttpResult<SingerContentInfo>> getSingerContent(@Body CollectContentReq homeContentReq);
    /**
     * 收藏内容
     */
    @POST("appControlDoctor/content/collect")
    Observable<HttpResult<com.cs.networklibrary.entity.NoData>> collectContent(@Body CollectContentReq homeContentReq);

    /**
     * /**
     * 取消收藏内容
     */
    @POST("appControlDoctor/content/cancelCollect")
    Observable<HttpResult<com.cs.networklibrary.entity.NoData>> collectContentCancel(@Body CollectContentReq homeContentReq);
    /**
     * 开处方
     */
    @POST("appControlDoctor/prescription/add")
    Observable<HttpResult<PrescriptionInfo>> addPrescription(@Body AddPrescriptionReq baseReq);
    /**
     * 获取历史处方
     */
    @POST("appControlDoctor/historicalPrescriptions")
    Observable<HttpResult<List<HistoryPrescriptionInfo>>> requestHistoryPrescription(@Body HistoryPrescriptionReq baseReq);
    /**
     * 获取单个处方
     */
    @POST("appControlDoctor/prescription/info")
    Observable<HttpResult<SingerPrescriptionInfo>> getPrescription(@Body GetSingerPrescriptionReq baseReq);
}
