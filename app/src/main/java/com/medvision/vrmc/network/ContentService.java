package com.medvision.vrmc.network;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

import com.cs.networklibrary.entity.NoData;
import com.medvision.vrmc.bean.FilterDisease;
import com.medvision.vrmc.bean.FilterType;
import com.medvision.vrmc.bean.HistoryPrescriptionInfo;
import com.medvision.vrmc.bean.HomeContent;
import com.medvision.vrmc.bean.MyPatientDetil;
import com.medvision.vrmc.bean.MyPatients;
import com.medvision.vrmc.bean.MyPlans;
import com.medvision.vrmc.bean.NewsInfo;
import com.medvision.vrmc.bean.PlanOfDisease;
import com.medvision.vrmc.bean.PrescriptionInfo;
import com.medvision.vrmc.bean.SingerContentInfo;
import com.medvision.vrmc.bean.SingerPrescriptionInfo;
import com.medvision.vrmc.bean.SingerSchemeInfo;
import com.medvision.vrmc.bean.requestbody.AddPatientreq;
import com.medvision.vrmc.bean.requestbody.AddPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.CollectPlanReq;
import com.medvision.vrmc.bean.requestbody.GetSingerPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.HistoryPrescriptionReq;
import com.medvision.vrmc.bean.requestbody.HomeContentReq;
import com.medvision.vrmc.bean.requestbody.ModifPatientreq;
import com.medvision.vrmc.bean.requestbody.PagingReq;
import com.medvision.vrmc.bean.requestbody.Patientreq;

import com.medvision.vrmc.bean.AddPatientInfo;
import com.medvision.vrmc.bean.ModifPatientInfo;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;
import com.medvision.vrmc.bean.requestbody.PushPlanReq;
import com.medvision.vrmc.bean.requestbody.planofdiseaseReq;
import com.medvision.vrmc.bean.requestbody.schemeReq;
import com.medvision.vrmc.bean.requestbody.schemedeleteReq;
import com.medvision.vrmc.bean.requestbody.schememodifyReq;

/**
 * Created by raytine on 2017/7/11.
 */

public interface ContentService {
    /**
     * 获取病症
     *
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/diseaseList")
    Observable<HttpResult<List<FilterDisease>>> getFilterDisease(@Body BaseReq baseReq);

    /**
     * 添加患者
     *
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/addPatient")
    Observable<HttpResult<AddPatientInfo>> addPatient(@Body AddPatientreq baseReq);

    /**
     * 获取我的患者
     */
    @POST("appControlDoctor/myPatients")
    Observable<HttpResult<List<MyPatients>>> getMyPatient(@Body BaseReq baseReq);

    /**
     * 我的患者详情
     *
     * @param baseReq
     * @return
     */
    @POST("appControlDoctor/patientInformations")
    Observable<HttpResult<MyPatientDetil>> getMyPatientDetil(@Body Patientreq baseReq);

    /**
     * 修改患者信息
     *
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
     * 获取历史方案
     */
    @POST("appControlDoctor/patientPlanPage")
    Observable<HttpResult<List<PlanOfDisease>>> requestHistoryPlan(@Body HistoryPrescriptionReq baseReq);

    /**
     * 获取单个处方
     */
    @POST("appControlDoctor/prescription/info")
    Observable<HttpResult<SingerPrescriptionInfo>> getPrescription(@Body GetSingerPrescriptionReq baseReq);

    /**
     * 终止单个处方
     */
    @POST("appControlDoctor/endPrescription")
    Observable<HttpResult<NoData>> endPrescription(@Body GetSingerPrescriptionReq baseReq);

    /**
     * 增加方案
     */
    @POST("appControlDoctor/addPlan")
    Observable<HttpResult<NoData>> addScheme(@Body schemeReq baseReq);

    /**
     * 获取我的方案
     */
    @POST("appControlDoctor/myPlans")
    Observable<HttpResult<List<SingerSchemeInfo>>> getScheme(@Body BaseReq baseReq);

    /**
     * 修改方案
     */
    @POST("appControlDoctor/upPlan")
    Observable<HttpResult<NoData>> modifyScheme(@Body schememodifyReq baseReq);

    /**
     * 修改方案
     */
    @POST("appControlDoctor/deletePlan")
    Observable<HttpResult<NoData>> deleteScheme(@Body schemedeleteReq baseReq);

    /**
     * 获取我的方案
     */
    @POST("appControlDoctor/collectedPlansList")
    Observable<HttpResult<MyPlans>> requeatMyPlans(@Body PagingReq baseReq);

    /**
     * 筛选方案接口
     */
    @POST("appControlDoctor/plansList")
    Observable<HttpResult<List<PlanOfDisease>>> requeatMyPlansOfDieasea(@Body planofdiseaseReq baseReq);

    /**
     * 收藏方案接口
     */
    @POST("appControlDoctor/collectPlan")
    Observable<HttpResult<NoData>> collectPlan(@Body CollectPlanReq baseReq);

    /**
     * 取消收藏方案接口
     */
    @POST("appControlDoctor/cancelCollectPlan")
    Observable<HttpResult<NoData>> cancelCollectPlan(@Body CollectPlanReq baseReq);

    /**
     * 获取收藏的方案列表接口
     */
    @POST("appControlDoctor/collectedPlansList")
    Observable<HttpResult<List<PlanOfDisease>>> collectedPlansList(@Body PagingReq baseReq);

    /**
     * 发送方案接口
     */
    @POST("appControlDoctor/sendPlan")
    Observable<HttpResult<NoData>> pushPlan(@Body PushPlanReq baseReq);
}
