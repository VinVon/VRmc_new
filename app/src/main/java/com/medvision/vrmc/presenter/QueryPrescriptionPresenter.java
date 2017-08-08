package com.medvision.vrmc.presenter;

import android.os.Handler;
import android.util.Log;

import com.medvision.vrmc.bean.NophonePrescriptionContent;
import com.medvision.vrmc.imp.QueryPrescription;
import com.medvision.vrmc.model.QueryPrescriptionModel;

import java.util.Map;

import com.medvision.vrmc.bean.Prescription;
import com.medvision.vrmc.bean.PrescriptionContent;

/**
 * Created by raytine on 2017/2/11.
 */

public class QueryPrescriptionPresenter {
    private Handler handler = new Handler();
    private QueryPrescription queryPrescription;
    private QueryPrescriptionModel queryPrescriptionModel;
    private Map<String,Object> map;

    public QueryPrescriptionPresenter(QueryPrescription queryPrescription) {
        this.queryPrescription = queryPrescription;
        queryPrescriptionModel = new QueryPrescriptionModel();
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    //获取处方
    public void  getPrescription(){
        queryPrescriptionModel.getPrescription(map, new QueryPrescriptionModel.OnLoginListener() {
            @Override
            public void loginSuccess(final Prescription user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.updateView(user);
                        queryPrescription.hideProgressDialog();
                    }
                });

            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.tokenchange();
                        queryPrescription.hideProgressDialog();
                    }
                });
            }

            @Override
            public void loginFailed(final Prescription user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.showError(user.getMessage());
                        queryPrescription.hideProgressDialog();
                    }
                });
            }
        });

    }
    //获取内容列表
    public void  getPrescriptionlist(){
        queryPrescriptionModel.getPrescriptionlist(map, new QueryPrescriptionModel.OnLoginListenero() {
            @Override
            public void loginSuccess(final PrescriptionContent user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.update(user);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.tokenchange();

                    }
                });
            }

            @Override
            public void loginFailed(final PrescriptionContent user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (user == null){
                            queryPrescription.showError(null);
                        }else{
                        queryPrescription.showError(user.getMessage());
                    }
                    }
                });
            }
        });
    }
    //获取内容列表(病案号)status1正常请求数据,2加载更多数据 3.刷新数据
    public void  getPrescriptionContentNophonelist(int status){
        queryPrescriptionModel.getPrescriptionlistnophone(map, new QueryPrescriptionModel.OnLoginListeneros() {
            @Override
            public void loginSuccess(final NophonePrescriptionContent user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("----bahpre",user.getData().size()+"个");
                        queryPrescription.update(user,status);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.tokenchange();

                    }
                });
            }

            @Override
            public void loginFailed(final NophonePrescriptionContent user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.showError(user.getMessage());
                    }
                });
            }
        });
    }

}
