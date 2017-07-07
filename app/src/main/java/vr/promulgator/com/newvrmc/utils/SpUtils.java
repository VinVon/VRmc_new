    package vr.promulgator.com.newvrmc.utils;

    /**
     * Created by raytine on 2017/2/28.
     */
    import android.content.Context;
    import android.util.Log;


    import vr.promulgator.com.newvrmc.bean.LocalInfo;
    import vr.promulgator.com.newvrmc.bean.LoginInfo;

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
                Log.e("-----------","用户信息为空");
                return null;
            }
        }
        public LocalInfo getUser() {
            Object module = getModule(SpDictionary.SP_USER);
            if (module != null) {
                return (LocalInfo) module;
            } else {
                Log.e("-----------","用户信息为空");
                return null;
            }
        }
        public boolean getFirstLogin(){
            boolean aBoolean = getBoolean(SpDictionary.TEXT);
            return  aBoolean;
        }
//        public String getLastAyncTime(){
//            String string = getString(SpDictionary.SYNC_TIME);
//            return string;
//        }
//        public void saveLastAynvTime(String value){
//            putString(SpDictionary.SYNC_TIME,value);
//        }
        public void saveUser(LocalInfo user) {
            putModule(SpDictionary.SP_USER, user);
        }
        public void saveLogin(LoginInfo user) {
            putModule(SpDictionary.SP_LOGIN, user);
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
