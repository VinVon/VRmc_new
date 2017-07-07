package vr.promulgator.com.newvrmc.imp;

/**
 * Created by raytine on 2017/4/14.
 */

public interface TaskStatus extends BaseImp {
    void getstates(int code);
    void getfailed(String msg);
}
