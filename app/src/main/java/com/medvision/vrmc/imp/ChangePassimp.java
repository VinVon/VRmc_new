package com.medvision.vrmc.imp;

/**
 * Created by raytine on 2017/4/25.
 */

public interface ChangePassimp extends BaseImp {
    public  void changeSuccess(String st);
    public  void changeFailed(String st);
    void showProgressDialog();

    void hideProgressDialog();
}
