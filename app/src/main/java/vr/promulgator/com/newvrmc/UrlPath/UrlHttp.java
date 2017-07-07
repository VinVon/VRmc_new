package vr.promulgator.com.newvrmc.UrlPath;

/**
 * Created by raytine on 2017/2/8.
 */

public class UrlHttp {
    //测试地址
    public static final String BASE_URL="http://test.med-vision.cn/api/v1/";
    //正式地址
//    public static final String BASE_URL="http://support.med-vision.cn/api/v1/";
    public static final String PATH_LOGIN =BASE_URL+"appControlVrRoom/login";
    //获取患者接口
    public static final String PATH_PATIENT=BASE_URL+"appControlVrRoom/patient/getByKeyword";
    //根据病案号获取患者接口
    public static final String PATH_PATIENT_NOPHONE=BASE_URL+"appControlVrRoom/getPatientByKeywordCase";
    //获取患者处方列表接口
    public static final String PATH_PRESCRIPTION = BASE_URL+"appControlVrRoom/prescription/getByPatientId";
    //获取内容列表接口
    public static final String PATH_PRESCRIPTIONCONTENT=BASE_URL+"appControlVrRoom/prescriptionContent/getByPrescriptionId";
    //获取内容列表接口(病案号版本)
    public static final String PATH_PRESCRIPTIONCONTENT_NOPHONE=BASE_URL+"appControlVrRoom/prescriptionCase/getContent";
    //获取版本信息
    public  static final String PATH_VERSION=BASE_URL+"appControlVrRoom/getVersion";
    //获取设备
    public  static  final  String PATH_GETUSERLIST = BASE_URL+"appControlVrRoom/userVrRoom/list";
    //添加任务
    public  static  final  String PATH_ADDTASK= BASE_URL+"appControlVrRoom/task/add";
    //退出登录
    public  static  final  String PATH_LOUUT=BASE_URL+"appControlVrRoom/logout";
    //修改密码
    public  static  final  String PATH_CHANGE_PASS=BASE_URL+"appControlVrRoom/modifyPassword";
}
