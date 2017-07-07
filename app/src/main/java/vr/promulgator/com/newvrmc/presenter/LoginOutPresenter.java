package vr.promulgator.com.newvrmc.presenter;

import android.os.Handler;

import vr.promulgator.com.newvrmc.model.LoginOutModel;
import vr.promulgator.com.newvrmc.bean.NoData;

import java.util.Map;

import vr.promulgator.com.newvrmc.imp.LoginOutimp;

/**
 * Created by raytine on 2017/4/25.
 */

public class LoginOutPresenter {
    private LoginOutimp loginOutimp;
    private LoginOutModel loginOutModel ;
    private Handler handler = new Handler();

    public LoginOutPresenter(LoginOutimp loginOutimp) {
        this.loginOutimp = loginOutimp;
        loginOutModel = new LoginOutModel();

    }
    public  void  logout(Map<String,String> map){
        loginOutModel.getUser(map, new LoginOutModel.OnLoginOutListener() {
            @Override
            public void loginoutSuccess(NoData user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginOutimp.outSuccess(user.getMessage());
                    }
                });
            }

            @Override
            public void loginoutFailed(NoData user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginOutimp.outSuccess(user.getMessage());
                    }
                });
            }
        });
    }
}
