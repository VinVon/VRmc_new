package vr.promulgator.com.newvrmc.presenter;

import android.os.Handler;

import vr.promulgator.com.newvrmc.imp.ChangePassimp;

import java.util.Map;

import vr.promulgator.com.newvrmc.bean.NoData;
import vr.promulgator.com.newvrmc.model.ModifyPassModel;

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
        loginOutModel.modify(map, new ModifyPassModel.ModifyListener() {
            @Override
            public void modifySuccess(NoData user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginOutimp.changeSuccess(user.getMessage());
                    }
                });
            }

            @Override
            public void modifyFailed(NoData user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginOutimp.changeFailed(user.getMessage());
                    }
                });
            }
        });
    }
}
