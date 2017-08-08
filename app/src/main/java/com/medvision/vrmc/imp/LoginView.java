package com.medvision.vrmc.imp;

import com.medvision.vrmc.bean.LoginInfo;

/**
 * Created by raytine on 2017/2/11.
 */

public interface LoginView {
    void updateView(LoginInfo user);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);
}
