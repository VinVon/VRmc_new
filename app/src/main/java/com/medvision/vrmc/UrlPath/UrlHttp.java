package com.medvision.vrmc.UrlPath;

/**
 * Created by raytine on 2017/2/8.
 */

public class UrlHttp {
    public static final String BASE_URL_EVERYTHING = "http://test.med-vision.cn/";
//    public static final String BASE_URL_EVERYTHING = "http://support.med-vision.cn/";
    //测试地址
    public static final String BASE_URL="http://test.med-vision.cn/api/v1/appControlDoctor";
    //正式地址
//    public static final String BASE_URL="http://support.med-vision.cn/api/v1/appControlDoctor";
    public static final String PATH_LOGIN =BASE_URL+"/login";
    //获取患者接口
    public static final String PATH_PATIENT=BASE_URL+"/patient/getByKeyword";
    //根据病案号获取患者接口
    public static final String PATH_PATIENT_NOPHONE=BASE_URL+"/getPatientByKeywordCase";
    //获取患者处方列表接口
    public static final String PATH_PRESCRIPTION = BASE_URL+"/prescription/getByPatientId";
    //获取内容列表接口
    public static final String PATH_PRESCRIPTIONCONTENT=BASE_URL+"/prescriptionContent/getByPrescriptionId";
    //获取内容列表接口(病案号版本)
    public static final String PATH_PRESCRIPTIONCONTENT_NOPHONE=BASE_URL+"/prescriptionCase/getContent";
    //获取版本信息
    public  static final String PATH_VERSION=BASE_URL+"/getVersion";
    //获取设备
    public  static  final  String PATH_GETUSERLIST = BASE_URL+"/userVrRoom/list";
    //添加任务
    public  static  final  String PATH_ADDTASK= BASE_URL+"/task/add";
    //退出登录
    public  static  final  String PATH_LOUUT=BASE_URL+"/logout";
    //修改密码
    public  static  final  String PATH_CHANGE_PASS=BASE_URL+"/modifyPassword";
}
