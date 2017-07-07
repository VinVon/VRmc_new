package vr.promulgator.com.newvrmc.imp;

import vr.promulgator.com.newvrmc.bean.UserList;

/**
 * Created by raytine on 2017/4/14.
 */

public interface UserListimp extends BaseImp{
     void success(UserList s);
    void  failed(String s);
}
