package vr.promulgator.com.newvrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.promulgator.com.newvrmc.bean.LoginInfo;
import vr.promulgator.com.newvrmc.imp.LoginView;
import vr.promulgator.com.newvrmc.model.LoginModel;

/**
 * Created by raytine on 2017/2/11.
 */

public class LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;
    private Map<String,String>map;
    private Handler mHandler = new Handler();
    public LoginPresenter(LoginView loginView,Map<String,String> map) {
        this.loginView = loginView;
        loginModel = new LoginModel();
        this.map = map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void Login(){
        loginView.showProgressDialog();
         loginModel.getUser(map, new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(final LoginInfo user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.updateView(user);
                        loginView.hideProgressDialog();
                    }
                });
            }



             @Override
            public void loginFailed(final LoginInfo user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showError(user.getMessage());
                        loginView.hideProgressDialog();
                    }
                });
            }
        });


    }
}
