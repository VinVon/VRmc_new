package vr.promulgator.com.newvrmc.presenter;

import android.os.Handler;

import vr.promulgator.com.newvrmc.bean.UserList;

import java.util.Map;

import vr.promulgator.com.newvrmc.imp.UserListimp;
import vr.promulgator.com.newvrmc.model.UsetListModel;

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
