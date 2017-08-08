package com.medvision.vrmc.presenter;

import android.os.Handler;

import com.medvision.vrmc.imp.ChangePassimp;
import com.medvision.vrmc.bean.NoData;
import com.medvision.vrmc.model.ModifyPassModel;

import java.util.Map;

/**
 * Created by raytine on 2017/4/25.
 */

public class ModifyPassPresenter {
    private ChangePassimp loginOutimp;
    private ModifyPassModel loginOutModel ;
    private Handler handler = new Handler();

    public ModifyPassPresenter(ChangePassimp loginOutimp) {
        this.loginOutimp = loginOutimp;
        loginOutModel = new ModifyPassModel();

    }
    public  void  modify(Map<String,String> map){
        loginOutimp.showProgressDialog();
        loginOutModel.modify(map, new ModifyPassModel.ModifyListener() {
            @Override
            public void modifySuccess(NoData user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginOutimp.changeSuccess(user.getMessage());
                        loginOutimp.hideProgressDialog();
                    }
                });
            }

            @Override
            public void modifyFailed(NoData user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginOutimp.changeFailed(user.getMessage());
                        loginOutimp.hideProgressDialog();
                    }
                });
            }
        });
    }
}
