package com.medvision.vrmc.imp;

import com.medvision.vrmc.bean.UserList;

/**
 * Created by raytine on 2017/4/14.
 */

public interface UserListimp extends BaseImp{
     void success(UserList s);
    void  failed(String s);
}
