package vr.promulgator.com.newvrmc.imp;

import vr.promulgator.com.newvrmc.bean.LoginInfo;

/**
 * Created by raytine on 2017/2/11.
 */

public interface LoginView {
    void updateView(LoginInfo user);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);
}
