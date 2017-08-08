package com.medvision.vrmc.imp;

import com.medvision.vrmc.bean.NophonePrescriptionContent;
import com.medvision.vrmc.bean.Prescription;
import com.medvision.vrmc.bean.PrescriptionContent;

/**
 * Created by raytine on 2017/2/11.
 */

public interface QueryPrescription extends BaseImp{
    //处方列表
    void updateView(Prescription user);
    //内容列表
    void update(PrescriptionContent p);
    //内容列表(病案号)
    void update(NophonePrescriptionContent p, int status);
    void showProgressDialog();
    void hideProgressDialog();
    void showError(String msg);

}
