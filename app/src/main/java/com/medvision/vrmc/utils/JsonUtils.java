package com.medvision.vrmc.utils;

import com.medvision.vrmc.bean.LoginInfo;
import com.medvision.vrmc.bean.PatientNophoneInfo;
import com.medvision.vrmc.bean.PatientInfo;
import com.medvision.vrmc.bean.Prescription;
import com.medvision.vrmc.bean.PrescriptionContent;
import com.medvision.vrmc.update.VersionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/2/9.
 */

public class JsonUtils {
    public static VersionInfo jsonVersion(String info){
        VersionInfo infos  = new VersionInfo();
        try {
            JSONObject object  = new JSONObject(info);
            infos.setSuccess(object.getBoolean("success"));
            infos.setCode(object.getInt("code"));
            infos.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            VersionInfo.DataBean dataBean = new VersionInfo.DataBean();
            dataBean.setId(obj.getString("id"));
            dataBean.setCreatedAt(obj.getString("createdAt"));
            dataBean.setCreator(obj.getString("creator"));
            dataBean.setUpdator(obj.getString("updator"));
            dataBean.setUpdatedAt(obj.getString("updatedAt"));
            dataBean.setAppCode(obj.getString("appCode"));
            dataBean.setDownloadUrl(obj.getString("downloadUrl"));
            dataBean.setVersionCode(obj.getString("versionCode"));
            dataBean.setVersionDesc(obj.getString("versionDesc"));
            dataBean.setVersionName(obj.getString("versionName"));
            dataBean.setVersionSize(obj.getString("versionSize"));
            infos.setData(dataBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }
    public static LoginInfo jsonPares(String info){
        LoginInfo infos  = new LoginInfo();
        try {
            JSONObject object  = new JSONObject(info);
            infos.setSuccess(object.getBoolean("success"));
            infos.setCode(object.getInt("code"));
            infos.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            LoginInfo.DataBean dataBean = new LoginInfo.DataBean();
            dataBean.setUserId(obj.getString("userId"));
            dataBean.setUsername(obj.getString("username"));
            dataBean.setRealname(obj.getString("realname"));
            dataBean.setToken(obj.getString("token"));
            infos.setData(dataBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }
    public static PatientInfo patientPares(String info){
        PatientInfo patientInfo = new PatientInfo();
        try {
            JSONObject object = new JSONObject(info);
            patientInfo.setSuccess(object.getBoolean("success"));
            patientInfo.setCode(object.getInt("code"));
            patientInfo.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            PatientInfo.DataBean pBean = new PatientInfo.DataBean();
            pBean.setToken(obj.getString("token"));
            pBean.setUserId(obj.getString("userId"));
            pBean.setRealname(obj.getString("realname"));
            pBean.setEmail(obj.getString("email"));
            pBean.setMobile(obj.getString("mobile"));
            pBean.setMedicareType(obj.getString("medicareType"));
            pBean.setMedicareCardNumber(obj.getString("medicareCardNumber"));
            pBean.setIdNumber(obj.getString("idNumber"));
            pBean.setAge(obj.get("age"));
            pBean.setAddress(obj.getString("address"));
            pBean.setEmergencyContact(obj.getString("emergencyContact"));
            pBean.setEmergencyContactPhone(obj.getString("emergencyContactPhone"));
            pBean.setGender(obj.get("gender"));
            patientInfo.setData(pBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patientInfo;
    }

    /**
     * 解析病案号患者
     * @param info
     * @return
     */
    public static PatientNophoneInfo patientnophonePares(String info){
        PatientNophoneInfo patientInfo = new PatientNophoneInfo();
        try {
            JSONObject object = new JSONObject(info);
            patientInfo.setSuccess(object.getBoolean("success"));
            patientInfo.setCode(object.getInt("code"));
            patientInfo.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            PatientNophoneInfo.DataBean pBean = new PatientNophoneInfo.DataBean();
            pBean.setToken(obj.getString("token"));
            pBean.setUserId(obj.getString("userId"));
            pBean.setRealname(obj.getString("realname"));
            pBean.setEmail(obj.getString("email"));
            pBean.setMobile(obj.getString("mobile"));
            pBean.setRecordno(obj.getString("recordno"));
            pBean.setMedicareType(obj.getInt("medicareType"));
            pBean.setMedicareCardNumber(obj.getString("medicareCardNumber"));
            pBean.setIdNumber(obj.getString("idNumber"));
            pBean.setAge(obj.getInt("age"));
            pBean.setAddress(obj.getString("address"));
            pBean.setEmergencyContact(obj.getString("emergencyContact"));
            pBean.setEmergencyContactPhone(obj.getString("emergencyContactPhone"));
            pBean.setGender(obj.getInt("gender"));
            patientInfo.setData(pBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patientInfo;
    }
    //解析处方列表
    public static Prescription prescriptionPares(String info){
        Prescription prescription = new Prescription();
        try {
            JSONObject object = new JSONObject(info);
            prescription.setSuccess(object.getBoolean("success"));
            prescription.setCode(object.getInt("code"));
            prescription.setMessage(object.getString("message"));
            JSONArray array = object.getJSONArray("data");
            List<Prescription.DataBean> mlist = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Prescription.DataBean presBean = new Prescription.DataBean();
                presBean.setId(obj.getString("id"));
                presBean.setCreator(obj.getString("creator"));
                presBean.setCreatedAt(obj.getString("createdAt"));
                presBean.setUpdator(obj.getString("updator"));
                presBean.setUpdatedAt(obj.getString("updatedAt"));
                presBean.setStatus(obj.getInt("status"));
//                presBean.setTotal(obj.getInt("total"));
                presBean.setSuggestion(obj.getString("suggestion"));
                presBean.setDoctorId(obj.getString("doctorId"));
                presBean.setPatientId(obj.getString("patientId"));
                presBean.setSource(obj.getInt("source"));
                presBean.setDoctorName(obj.getString("doctorName"));
                presBean.setAdmissionNumber(obj.getString("admissionNumber"));
                presBean.setOutpatientNumber(obj.getString("outpatientNumber"));
                presBean.setDisease(obj.getString("disease"));
                presBean.setHidden(obj.getInt("hidden"));
                mlist.add(presBean);
            }
            prescription.setData(mlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prescription;
    }
    //解析内容列表
    public static PrescriptionContent prescriptionContentParess(String infos){
        PrescriptionContent prescriptionc = new PrescriptionContent();
        try {
            JSONObject object = new JSONObject(infos);
            prescriptionc.setSuccess(object.getBoolean("success"));
            prescriptionc.setCode(object.getInt("code"));
            prescriptionc.setMessage(object.getString("message"));
            JSONArray array = object.getJSONArray("data");
            List<PrescriptionContent.DataBean> mlist = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                PrescriptionContent.DataBean presBean = new PrescriptionContent.DataBean();
//                presBean.setUnitPrice(obj.getDouble("unitPrice"));
                presBean.setContent_name(obj.getString("content_name"));
//                presBean.setPrescriptionId(obj.getString("prescriptionId"));
//                presBean.setPeriod(obj.getInt("period"));
//                presBean.setCreator(obj.getString("creator"));
                presBean.setContentId(obj.getString("contentId"));
//                presBean.setPeriodUnit(obj.getInt("periodUnit"));
//                presBean.setFrequency(obj.getInt("frequency"));
//                presBean.setContent_helpCode(obj.getString("content_helpCode"));
//                presBean.setCreatedAt(obj.getString("createdAt"));
                presBean.setTimes(obj.getInt("times"));
                presBean.setContent_type(obj.getInt("content_type"));
//                presBean.setPrice(obj.getDouble("price"));
//                presBean.setLastUseAt(obj.getString("lastUseAt"));
//                presBean.setUpdator(obj.getString("updator"));
                presBean.setId(obj.getString("id"));
//                presBean.setStatus(obj.getString("status"));
                presBean.setUseTimes(obj.getInt("useTimes"));
                presBean.setUpdatedAt(obj.getString("updatedAt"));
                mlist.add(presBean);
            }
            prescriptionc.setData(mlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prescriptionc;
    }

}
