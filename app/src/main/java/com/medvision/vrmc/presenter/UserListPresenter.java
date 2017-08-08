package com.medvision.vrmc.presenter;

import android.os.Handler;

import com.medvision.vrmc.model.UsetListModel;
import com.medvision.vrmc.bean.UserList;
import com.medvision.vrmc.imp.UserListimp;

import java.util.Map;

/**
 * Created by raytine on 2017/4/14.
 */

public class UserListPresenter {
    private UserListimp userListimp;
    private UsetListModel usetListModel;
    private Handler handler = new Handler();

    public UserListPresenter(UserListimp userListimp) {
        this.userListimp = userListimp;
        usetListModel = new UsetListModel();
    }
    public void getUserList(Map<String,String> map){
        usetListModel.getUserList(map, new UsetListModel.GetUserListLisencer() {
            @Override
            public void GetUserListSuccess(UserList user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userListimp.success(user);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                userListimp.tokenchange();
                    }
                });
            }

            @Override
            public void GetUserListFailed(String user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userListimp.failed(user);
                    }
                });
            }
        });
    }
}
