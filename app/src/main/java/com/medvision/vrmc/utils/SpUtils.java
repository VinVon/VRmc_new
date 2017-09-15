    package com.medvision.vrmc.utils;

    /**
     * Created by raytine on 2017/2/28.
     */
    import android.content.Context;


    import com.medvision.vrmc.bean.CertifiStatus;
    import com.medvision.vrmc.bean.LoginInfo;
    import com.medvision.vrmc.bean.LocalInfo;

    public class SpUtils extends SpUtilsBase {
        private static SpUtils instance;

        public SpUtils() {
        }

        public static SpUtils getInstance() {
            if (instance == null) {
                instance = new SpUtils();
                return instance;
            } else {
                return instance;
            }
        }

        public void init(Context context) {
            if (mAppPreferences == null) {
                mAppPreferences = new MyModulePreference(context, context.getPackageName());
            }
        }
        public LoginInfo getLogin(){
            Object module = getModule(SpDictionary.SP_LOGIN);
            if (module != null) {
                return (LoginInfo) module;
            } else {
                MyLog.e("-----------","用户信息为空");
                return null;
            }
        }
        public LocalInfo getUser() {
            Object module = getModule(SpDictionary.SP_USER);
            if (module != null) {
                return (LocalInfo) module;
            } else {
                MyLog.e("-----------","用户信息为空");
                return null;
            }
        }
        public CertifiStatus getUserStatus() {
            Object module = getModule(SpDictionary.SP_STATUS);
            if (module != null) {
                return (CertifiStatus) module;
            } else {
                MyLog.e("-----------","用户信息为空");
                return null;
            }
        }
        public boolean getFirstLogin(){
            boolean aBoolean = getBoolean(SpDictionary.TEXT);
            return  aBoolean;
        }
        public void saveUser(LocalInfo user) {
            putModule(SpDictionary.SP_USER, user);
        }
        public void saveLogin(LoginInfo user) {
            putModule(SpDictionary.SP_LOGIN, user);
        }
        public void saveUserStatus(CertifiStatus status) {
            putModule(SpDictionary.SP_STATUS, status);
        }
        public void saveFirstLogin(boolean is){putBoolean(SpDictionary.TEXT,is);}

        public String getToken() {
            LocalInfo user = getUser();
            if (user != null) {
                return user.getToken();
            }
            return "";
        }
    }
